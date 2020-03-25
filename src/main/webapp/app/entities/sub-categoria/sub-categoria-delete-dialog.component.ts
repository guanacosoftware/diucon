import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubCategoria } from 'app/shared/model/sub-categoria.model';
import { SubCategoriaService } from './sub-categoria.service';

@Component({
  templateUrl: './sub-categoria-delete-dialog.component.html'
})
export class SubCategoriaDeleteDialogComponent {
  subCategoria?: ISubCategoria;

  constructor(
    protected subCategoriaService: SubCategoriaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subCategoriaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('subCategoriaListModification');
      this.activeModal.close();
    });
  }
}
