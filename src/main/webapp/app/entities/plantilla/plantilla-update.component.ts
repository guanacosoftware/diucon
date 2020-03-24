import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPlantilla, Plantilla } from 'app/shared/model/plantilla.model';
import { PlantillaService } from './plantilla.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ISubCategoria } from 'app/shared/model/sub-categoria.model';
import { SubCategoriaService } from 'app/entities/sub-categoria/sub-categoria.service';

@Component({
  selector: 'jhi-plantilla-update',
  templateUrl: './plantilla-update.component.html'
})
export class PlantillaUpdateComponent implements OnInit {
  isSaving = false;
  subcategorias: ISubCategoria[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    cuerpo: [null, [Validators.required]],
    subcategorias: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected plantillaService: PlantillaService,
    protected subCategoriaService: SubCategoriaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plantilla }) => {
      this.updateForm(plantilla);

      this.subCategoriaService.query().subscribe((res: HttpResponse<ISubCategoria[]>) => (this.subcategorias = res.body || []));
    });
  }

  updateForm(plantilla: IPlantilla): void {
    this.editForm.patchValue({
      id: plantilla.id,
      nombre: plantilla.nombre,
      cuerpo: plantilla.cuerpo,
      subcategorias: plantilla.subcategorias
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
    const plantilla = this.createFromForm();
    if (plantilla.id !== undefined) {
      this.subscribeToSaveResponse(this.plantillaService.update(plantilla));
    } else {
      this.subscribeToSaveResponse(this.plantillaService.create(plantilla));
    }
  }

  private createFromForm(): IPlantilla {
    return {
      ...new Plantilla(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      cuerpo: this.editForm.get(['cuerpo'])!.value,
      subcategorias: this.editForm.get(['subcategorias'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlantilla>>): void {
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

  trackById(index: number, item: ISubCategoria): any {
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
