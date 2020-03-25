import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlantilla } from 'app/shared/model/plantilla.model';
import { PlantillaService } from './plantilla.service';
import { PlantillaDeleteDialogComponent } from './plantilla-delete-dialog.component';

@Component({
  selector: 'jhi-plantilla',
  templateUrl: './plantilla.component.html'
})
export class PlantillaComponent implements OnInit, OnDestroy {
  plantillas?: IPlantilla[];
  eventSubscriber?: Subscription;

  constructor(
    protected plantillaService: PlantillaService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.plantillaService.query().subscribe((res: HttpResponse<IPlantilla[]>) => (this.plantillas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPlantillas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPlantilla): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInPlantillas(): void {
    this.eventSubscriber = this.eventManager.subscribe('plantillaListModification', () => this.loadAll());
  }

  delete(plantilla: IPlantilla): void {
    const modalRef = this.modalService.open(PlantillaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.plantilla = plantilla;
  }
}
