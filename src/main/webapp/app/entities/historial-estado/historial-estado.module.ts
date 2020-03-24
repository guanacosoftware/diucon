import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiuconSharedModule } from 'app/shared/shared.module';
import { HistorialEstadoComponent } from './historial-estado.component';
import { HistorialEstadoDetailComponent } from './historial-estado-detail.component';
import { HistorialEstadoUpdateComponent } from './historial-estado-update.component';
import { HistorialEstadoDeleteDialogComponent } from './historial-estado-delete-dialog.component';
import { historialEstadoRoute } from './historial-estado.route';

@NgModule({
  imports: [DiuconSharedModule, RouterModule.forChild(historialEstadoRoute)],
  declarations: [
    HistorialEstadoComponent,
    HistorialEstadoDetailComponent,
    HistorialEstadoUpdateComponent,
    HistorialEstadoDeleteDialogComponent
  ],
  entryComponents: [HistorialEstadoDeleteDialogComponent]
})
export class DiuconHistorialEstadoModule {}
