import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IHistorialEstado, HistorialEstado } from 'app/shared/model/historial-estado.model';
import { HistorialEstadoService } from './historial-estado.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IIncidente } from 'app/shared/model/incidente.model';
import { IncidenteService } from 'app/entities/incidente/incidente.service';

type SelectableEntity = IUser | IIncidente;

@Component({
  selector: 'jhi-historial-estado-update',
  templateUrl: './historial-estado-update.component.html'
})
export class HistorialEstadoUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  incidentes: IIncidente[] = [];

  editForm = this.fb.group({
    id: [],
    fecha: [null, [Validators.required]],
    estado: [],
    usuarioId: [],
    incidenteId: []
  });

  constructor(
    protected historialEstadoService: HistorialEstadoService,
    protected userService: UserService,
    protected incidenteService: IncidenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historialEstado }) => {
      if (!historialEstado.id) {
        const today = moment().startOf('day');
        historialEstado.fecha = today;
      }

      this.updateForm(historialEstado);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.incidenteService.query().subscribe((res: HttpResponse<IIncidente[]>) => (this.incidentes = res.body || []));
    });
  }

  updateForm(historialEstado: IHistorialEstado): void {
    this.editForm.patchValue({
      id: historialEstado.id,
      fecha: historialEstado.fecha ? historialEstado.fecha.format(DATE_TIME_FORMAT) : null,
      estado: historialEstado.estado,
      usuarioId: historialEstado.usuarioId,
      incidenteId: historialEstado.incidenteId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const historialEstado = this.createFromForm();
    if (historialEstado.id !== undefined) {
      this.subscribeToSaveResponse(this.historialEstadoService.update(historialEstado));
    } else {
      this.subscribeToSaveResponse(this.historialEstadoService.create(historialEstado));
    }
  }

  private createFromForm(): IHistorialEstado {
    return {
      ...new HistorialEstado(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      estado: this.editForm.get(['estado'])!.value,
      usuarioId: this.editForm.get(['usuarioId'])!.value,
      incidenteId: this.editForm.get(['incidenteId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistorialEstado>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
