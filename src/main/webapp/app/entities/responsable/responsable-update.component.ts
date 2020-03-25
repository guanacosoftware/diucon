import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IResponsable, Responsable } from 'app/shared/model/responsable.model';
import { ResponsableService } from './responsable.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ISubCategoria } from 'app/shared/model/sub-categoria.model';
import { SubCategoriaService } from 'app/entities/sub-categoria/sub-categoria.service';

type SelectableEntity = IUser | ISubCategoria;

@Component({
  selector: 'jhi-responsable-update',
  templateUrl: './responsable-update.component.html'
})
export class ResponsableUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  subcategorias: ISubCategoria[] = [];
  fechaNacimientoDp: any;

  editForm = this.fb.group({
    id: [],
    nombreCompleto: [null, [Validators.required]],
    telefono: [null, [Validators.required]],
    fechaNacimiento: [],
    dni: [null, [Validators.min(0), Validators.max(99999999)]],
    domicilio: [null, [Validators.required]],
    latitud: [],
    longitud: [],
    profesional: [],
    estudiante: [],
    trabajador: [],
    usuarioId: [],
    subcategorias: []
  });

  constructor(
    protected responsableService: ResponsableService,
    protected userService: UserService,
    protected subCategoriaService: SubCategoriaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsable }) => {
      this.updateForm(responsable);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.subCategoriaService.query().subscribe((res: HttpResponse<ISubCategoria[]>) => (this.subcategorias = res.body || []));
    });
  }

  updateForm(responsable: IResponsable): void {
    this.editForm.patchValue({
      id: responsable.id,
      nombreCompleto: responsable.nombreCompleto,
      telefono: responsable.telefono,
      fechaNacimiento: responsable.fechaNacimiento,
      dni: responsable.dni,
      domicilio: responsable.domicilio,
      latitud: responsable.latitud,
      longitud: responsable.longitud,
      profesional: responsable.profesional,
      estudiante: responsable.estudiante,
      trabajador: responsable.trabajador,
      usuarioId: responsable.usuarioId,
      subcategorias: responsable.subcategorias
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const responsable = this.createFromForm();
    if (responsable.id !== undefined) {
      this.subscribeToSaveResponse(this.responsableService.update(responsable));
    } else {
      this.subscribeToSaveResponse(this.responsableService.create(responsable));
    }
  }

  private createFromForm(): IResponsable {
    return {
      ...new Responsable(),
      id: this.editForm.get(['id'])!.value,
      nombreCompleto: this.editForm.get(['nombreCompleto'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      fechaNacimiento: this.editForm.get(['fechaNacimiento'])!.value,
      dni: this.editForm.get(['dni'])!.value,
      domicilio: this.editForm.get(['domicilio'])!.value,
      latitud: this.editForm.get(['latitud'])!.value,
      longitud: this.editForm.get(['longitud'])!.value,
      profesional: this.editForm.get(['profesional'])!.value,
      estudiante: this.editForm.get(['estudiante'])!.value,
      trabajador: this.editForm.get(['trabajador'])!.value,
      usuarioId: this.editForm.get(['usuarioId'])!.value,
      subcategorias: this.editForm.get(['subcategorias'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResponsable>>): void {
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

  getSelected(selectedVals: ISubCategoria[], option: ISubCategoria): ISubCategoria {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
