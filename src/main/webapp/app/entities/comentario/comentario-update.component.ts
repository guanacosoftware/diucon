import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IComentario, Comentario } from 'app/shared/model/comentario.model';
import { ComentarioService } from './comentario.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IIncidente } from 'app/shared/model/incidente.model';
import { IncidenteService } from 'app/entities/incidente/incidente.service';

type SelectableEntity = IUser | IIncidente;

@Component({
  selector: 'jhi-comentario-update',
  templateUrl: './comentario-update.component.html'
})
export class ComentarioUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  incidentes: IIncidente[] = [];

  editForm = this.fb.group({
    id: [],
    cuerpo: [null, [Validators.required]],
    usuarioId: [],
    incidenteId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected comentarioService: ComentarioService,
    protected userService: UserService,
    protected incidenteService: IncidenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comentario }) => {
      this.updateForm(comentario);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.incidenteService.query().subscribe((res: HttpResponse<IIncidente[]>) => (this.incidentes = res.body || []));
    });
  }

  updateForm(comentario: IComentario): void {
    this.editForm.patchValue({
      id: comentario.id,
      cuerpo: comentario.cuerpo,
      usuarioId: comentario.usuarioId,
      incidenteId: comentario.incidenteId
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('diuconApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comentario = this.createFromForm();
    if (comentario.id !== undefined) {
      this.subscribeToSaveResponse(this.comentarioService.update(comentario));
    } else {
      this.subscribeToSaveResponse(this.comentarioService.create(comentario));
    }
  }

  private createFromForm(): IComentario {
    return {
      ...new Comentario(),
      id: this.editForm.get(['id'])!.value,
      cuerpo: this.editForm.get(['cuerpo'])!.value,
      usuarioId: this.editForm.get(['usuarioId'])!.value,
      incidenteId: this.editForm.get(['incidenteId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComentario>>): void {
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
