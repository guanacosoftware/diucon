import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiuconSharedModule } from 'app/shared/shared.module';
import { ResponsableComponent } from './responsable.component';
import { ResponsableDetailComponent } from './responsable-detail.component';
import { ResponsableUpdateComponent } from './responsable-update.component';
import { ResponsableDeleteDialogComponent } from './responsable-delete-dialog.component';
import { responsableRoute } from './responsable.route';

@NgModule({
  imports: [DiuconSharedModule, RouterModule.forChild(responsableRoute)],
  declarations: [ResponsableComponent, ResponsableDetailComponent, ResponsableUpdateComponent, ResponsableDeleteDialogComponent],
  entryComponents: [ResponsableDeleteDialogComponent]
})
export class DiuconResponsableModule {}
