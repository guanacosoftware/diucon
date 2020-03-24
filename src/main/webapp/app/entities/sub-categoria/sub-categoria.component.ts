import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubCategoria } from 'app/shared/model/sub-categoria.model';
import { SubCategoriaService } from './sub-categoria.service';
import { SubCategoriaDeleteDialogComponent } from './sub-categoria-delete-dialog.component';

@Component({
  selector: 'jhi-sub-categoria',
  templateUrl: './sub-categoria.component.html'
})
export class SubCategoriaComponent implements OnInit, OnDestroy {
  subCategorias?: ISubCategoria[];
  eventSubscriber?: Subscription;

  constructor(
    protected subCategoriaService: SubCategoriaService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.subCategoriaService.query().subscribe((res: HttpResponse<ISubCategoria[]>) => (this.subCategorias = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSubCategorias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISubCategoria): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInSubCategorias(): void {
    this.eventSubscriber = this.eventManager.subscribe('subCategoriaListModification', () => this.loadAll());
  }

  delete(subCategoria: ISubCategoria): void {
    const modalRef = this.modalService.open(SubCategoriaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.subCategoria = subCategoria;
  }
}
