import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IComentario } from 'app/shared/model/comentario.model';
import { ComentarioService } from './comentario.service';
import { ComentarioDeleteDialogComponent } from './comentario-delete-dialog.component';

@Component({
  selector: 'jhi-comentario',
  templateUrl: './comentario.component.html'
})
export class ComentarioComponent implements OnInit, OnDestroy {
  comentarios?: IComentario[];
  eventSubscriber?: Subscription;

  constructor(
    protected comentarioService: ComentarioService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.comentarioService.query().subscribe((res: HttpResponse<IComentario[]>) => (this.comentarios = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInComentarios();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IComentario): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInComentarios(): void {
    this.eventSubscriber = this.eventManager.subscribe('comentarioListModification', () => this.loadAll());
  }

  delete(comentario: IComentario): void {
    const modalRef = this.modalService.open(ComentarioDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.comentario = comentario;
  }
}
