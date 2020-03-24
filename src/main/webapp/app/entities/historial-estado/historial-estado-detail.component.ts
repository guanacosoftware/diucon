import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHistorialEstado } from 'app/shared/model/historial-estado.model';

@Component({
  selector: 'jhi-historial-estado-detail',
  templateUrl: './historial-estado-detail.component.html'
})
export class HistorialEstadoDetailComponent implements OnInit {
  historialEstado: IHistorialEstado | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historialEstado }) => (this.historialEstado = historialEstado));
  }

  previousState(): void {
    window.history.back();
  }
}
