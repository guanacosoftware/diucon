import { MapsAPILoader } from '@agm/core';
import { HttpResponse } from '@angular/common/http';
import { Component, ElementRef, NgZone, OnInit, ViewChild } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { SubCategoriaService } from 'app/entities/sub-categoria/sub-categoria.service';
import { IResponsable, Responsable } from 'app/shared/model/responsable.model';
import { ISubCategoria } from 'app/shared/model/sub-categoria.model';
import { Observable } from 'rxjs';
import { ResponsableService } from './responsable.service';

type SelectableEntity = IUser | ISubCategoria;

@Component({
  selector: 'jhi-responsable-update',
  templateUrl: './responsable-update.component.html',
  styleUrls: ['./responsable-update.component.scss']
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

  @ViewChild('search', { static: false }) public searchElementRef: ElementRef;
  latitud: number | undefined;
  longitud: number | undefined;

  constructor(
    protected responsableService: ResponsableService,
    protected userService: UserService,
    protected subCategoriaService: SubCategoriaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private mapsAPILoader: MapsAPILoader,
    private ngZone: NgZone
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsable }) => {
      this.updateForm(responsable);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.subCategoriaService.query().subscribe((res: HttpResponse<ISubCategoria[]>) => (this.subcategorias = res.body || []));

      // load Places Autocomplete
      this.mapsAPILoader.load().then(() => {
        const autocomplete = new google.maps.places.Autocomplete(this.searchElementRef.nativeElement, {
          types: ['address']
        });
        autocomplete.addListener('place_changed', () => {
          this.ngZone.run(() => {
            // get the place result
            const place: google.maps.places.PlaceResult = autocomplete.getPlace();

            // verify result
            if (place.geometry === undefined || place.geometry === null) {
              this.latitud = undefined;
              this.longitud = undefined;
              this.editForm.patchValue({
                latitud: this.latitud,
                longitud: this.longitud
              });
              return;
            }

            // set latitude, longitude and zoom
            this.latitud = place.geometry.location.lat();
            this.longitud = place.geometry.location.lng();
            this.editForm.patchValue({
              domicilio: place.formatted_address,
              latitud: this.latitud,
              longitud: this.longitud
            });
          });
        });
      });
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
