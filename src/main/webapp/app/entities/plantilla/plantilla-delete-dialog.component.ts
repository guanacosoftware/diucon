import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlantilla } from 'app/shared/model/plantilla.model';
import { PlantillaService } from './plantilla.service';

@Component({
  templateUrl: './plantilla-delete-dialog.component.html'
})
export class PlantillaDeleteDialogComponent {
  plantilla?: IPlantilla;

  constructor(protected plantillaService: PlantillaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.plantillaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('plantillaListModification');
      this.activeModal.close();
    });
  }
}
