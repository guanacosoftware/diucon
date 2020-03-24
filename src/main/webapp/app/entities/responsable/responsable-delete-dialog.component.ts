import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResponsable } from 'app/shared/model/responsable.model';
import { ResponsableService } from './responsable.service';

@Component({
  templateUrl: './responsable-delete-dialog.component.html'
})
export class ResponsableDeleteDialogComponent {
  responsable?: IResponsable;

  constructor(
    protected responsableService: ResponsableService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.responsableService.delete(id).subscribe(() => {
      this.eventManager.broadcast('responsableListModification');
      this.activeModal.close();
    });
  }
}
