import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPlantilla } from 'app/shared/model/plantilla.model';

@Component({
  selector: 'jhi-plantilla-detail',
  templateUrl: './plantilla-detail.component.html'
})
export class PlantillaDetailComponent implements OnInit {
  plantilla: IPlantilla | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plantilla }) => (this.plantilla = plantilla));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
