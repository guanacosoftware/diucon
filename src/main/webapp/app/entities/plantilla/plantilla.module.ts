import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiuconSharedModule } from 'app/shared/shared.module';
import { PlantillaComponent } from './plantilla.component';
import { PlantillaDetailComponent } from './plantilla-detail.component';
import { PlantillaUpdateComponent } from './plantilla-update.component';
import { PlantillaDeleteDialogComponent } from './plantilla-delete-dialog.component';
import { plantillaRoute } from './plantilla.route';

@NgModule({
  imports: [DiuconSharedModule, RouterModule.forChild(plantillaRoute)],
  declarations: [PlantillaComponent, PlantillaDetailComponent, PlantillaUpdateComponent, PlantillaDeleteDialogComponent],
  entryComponents: [PlantillaDeleteDialogComponent]
})
export class DiuconPlantillaModule {}
