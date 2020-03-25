import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHistorialEstado } from 'app/shared/model/historial-estado.model';
import { HistorialEstadoService } from './historial-estado.service';

@Component({
  templateUrl: './historial-estado-delete-dialog.component.html'
})
export class HistorialEstadoDeleteDialogComponent {
  historialEstado?: IHistorialEstado;

  constructor(
    protected historialEstadoService: HistorialEstadoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.historialEstadoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('historialEstadoListModification');
      this.activeModal.close();
    });
  }
}
