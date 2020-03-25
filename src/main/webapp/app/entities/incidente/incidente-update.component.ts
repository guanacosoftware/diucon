import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { CategoriaService } from 'app/entities/categoria/categoria.service';
import { ResponsableService } from 'app/entities/responsable/responsable.service';
import { SubCategoriaService } from 'app/entities/sub-categoria/sub-categoria.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICategoria } from 'app/shared/model/categoria.model';
import { IIncidente, Incidente } from 'app/shared/model/incidente.model';
import { IResponsable } from 'app/shared/model/responsable.model';
import { ISubCategoria } from 'app/shared/model/sub-categoria.model';
import * as moment from 'moment';
import { JhiDataUtils, JhiEventManager, JhiEventWithContent, JhiFileLoadError } from 'ng-jhipster';
import { Observable } from 'rxjs';
import { IncidenteService } from './incidente.service';

type SelectableEntity = ICategoria | ISubCategoria | IUser | IResponsable;

@Component({
  selector: 'jhi-incidente-update',
  templateUrl: './incidente-update.component.html'
})
export class IncidenteUpdateComponent implements OnInit {
  isSaving = false;
  categorias: ICategoria[] = [];
  subcategorias: ISubCategoria[] = [];
  users: IUser[] = [];
  responsables: IResponsable[] = [];

  editForm = this.fb.group({
    id: [],
    fecha: [null, [Validators.required]],
    resumen: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    estado: [null, [Validators.required]],
    localizacion: [],
    latitud: [],
    longitud: [],
    fechaResolucion: [],
    fechaCierre: [],
    email: [null, [Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    categoriaId: [null, [Validators.required]],
    subcategoriaId: [null, [Validators.required]],
    operadorId: [],
    responsableId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected incidenteService: IncidenteService,
    protected categoriaService: CategoriaService,
    protected subCategoriaService: SubCategoriaService,
    protected userService: UserService,
    protected responsableService: ResponsableService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ incidente }) => {
      if (!incidente.id) {
        const today = moment().startOf('day');
        incidente.fecha = today;
        incidente.fechaResolucion = today;
        incidente.fechaCierre = today;
      }

      this.updateForm(incidente);

      this.categoriaService.query().subscribe((res: HttpResponse<ICategoria[]>) => (this.categorias = res.body || []));

      this.subCategoriaService.query().subscribe((res: HttpResponse<ISubCategoria[]>) => (this.subcategorias = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.responsableService.query().subscribe((res: HttpResponse<IResponsable[]>) => (this.responsables = res.body || []));
    });
  }

  updateForm(incidente: IIncidente): void {
    this.editForm.patchValue({
      id: incidente.id,
      fecha: incidente.fecha ? incidente.fecha.format(DATE_TIME_FORMAT) : null,
      resumen: incidente.resumen,
      descripcion: incidente.descripcion,
      estado: incidente.estado,
      localizacion: incidente.localizacion,
      latitud: incidente.latitud,
      longitud: incidente.longitud,
      fechaResolucion: incidente.fechaResolucion ? incidente.fechaResolucion.format(DATE_TIME_FORMAT) : null,
      fechaCierre: incidente.fechaCierre ? incidente.fechaCierre.format(DATE_TIME_FORMAT) : null,
      email: incidente.email,
      categoriaId: incidente.categoriaId,
      subcategoriaId: incidente.subcategoriaId,
      operadorId: incidente.operadorId,
      responsableId: incidente.responsableId
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
    const incidente = this.createFromForm();
    if (incidente.id !== undefined) {
      this.subscribeToSaveResponse(this.incidenteService.update(incidente));
    } else {
      this.subscribeToSaveResponse(this.incidenteService.create(incidente));
    }
  }

  private createFromForm(): IIncidente {
    return {
      ...new Incidente(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      resumen: this.editForm.get(['resumen'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      localizacion: this.editForm.get(['localizacion'])!.value,
      latitud: this.editForm.get(['latitud'])!.value,
      longitud: this.editForm.get(['longitud'])!.value,
      fechaResolucion: this.editForm.get(['fechaResolucion'])!.value
        ? moment(this.editForm.get(['fechaResolucion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaCierre: this.editForm.get(['fechaCierre'])!.value
        ? moment(this.editForm.get(['fechaCierre'])!.value, DATE_TIME_FORMAT)
        : undefined,
      email: this.editForm.get(['email'])!.value,
      categoriaId: this.editForm.get(['categoriaId'])!.value,
      subcategoriaId: this.editForm.get(['subcategoriaId'])!.value,
      operadorId: this.editForm.get(['operadorId'])!.value,
      responsableId: this.editForm.get(['responsableId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncidente>>): void {
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
