import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IComentario } from 'app/shared/model/comentario.model';

@Component({
  selector: 'jhi-comentario-detail',
  templateUrl: './comentario-detail.component.html'
})
export class ComentarioDetailComponent implements OnInit {
  comentario: IComentario | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comentario }) => (this.comentario = comentario));
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
