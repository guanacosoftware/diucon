import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHistorialEstado } from 'app/shared/model/historial-estado.model';
import { HistorialEstadoService } from './historial-estado.service';
import { HistorialEstadoDeleteDialogComponent } from './historial-estado-delete-dialog.component';

@Component({
  selector: 'jhi-historial-estado',
  templateUrl: './historial-estado.component.html'
})
export class HistorialEstadoComponent implements OnInit, OnDestroy {
  historialEstados?: IHistorialEstado[];
  eventSubscriber?: Subscription;

  constructor(
    protected historialEstadoService: HistorialEstadoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.historialEstadoService.query().subscribe((res: HttpResponse<IHistorialEstado[]>) => (this.historialEstados = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHistorialEstados();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHistorialEstado): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHistorialEstados(): void {
    this.eventSubscriber = this.eventManager.subscribe('historialEstadoListModification', () => this.loadAll());
  }

  delete(historialEstado: IHistorialEstado): void {
    const modalRef = this.modalService.open(HistorialEstadoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.historialEstado = historialEstado;
  }
}
