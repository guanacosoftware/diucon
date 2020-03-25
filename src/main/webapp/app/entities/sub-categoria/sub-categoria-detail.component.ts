import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISubCategoria } from 'app/shared/model/sub-categoria.model';

@Component({
  selector: 'jhi-sub-categoria-detail',
  templateUrl: './sub-categoria-detail.component.html'
})
export class SubCategoriaDetailComponent implements OnInit {
  subCategoria: ISubCategoria | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subCategoria }) => (this.subCategoria = subCategoria));
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
