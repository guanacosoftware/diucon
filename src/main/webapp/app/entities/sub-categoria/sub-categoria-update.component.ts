import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ISubCategoria, SubCategoria } from 'app/shared/model/sub-categoria.model';
import { SubCategoriaService } from './sub-categoria.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria/categoria.service';

@Component({
  selector: 'jhi-sub-categoria-update',
  templateUrl: './sub-categoria-update.component.html'
})
export class SubCategoriaUpdateComponent implements OnInit {
  isSaving = false;
  categorias: ICategoria[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    observaciones: [],
    categiaId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected subCategoriaService: SubCategoriaService,
    protected categoriaService: CategoriaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subCategoria }) => {
      this.updateForm(subCategoria);

      this.categoriaService.query().subscribe((res: HttpResponse<ICategoria[]>) => (this.categorias = res.body || []));
    });
  }

  updateForm(subCategoria: ISubCategoria): void {
    this.editForm.patchValue({
      id: subCategoria.id,
      nombre: subCategoria.nombre,
      observaciones: subCategoria.observaciones,
      categiaId: subCategoria.categiaId
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
    const subCategoria = this.createFromForm();
    if (subCategoria.id !== undefined) {
      this.subscribeToSaveResponse(this.subCategoriaService.update(subCategoria));
    } else {
      this.subscribeToSaveResponse(this.subCategoriaService.create(subCategoria));
    }
  }

  private createFromForm(): ISubCategoria {
    return {
      ...new SubCategoria(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      observaciones: this.editForm.get(['observaciones'])!.value,
      categiaId: this.editForm.get(['categiaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubCategoria>>): void {
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

  trackById(index: number, item: ICategoria): any {
    return item.id;
  }
}
