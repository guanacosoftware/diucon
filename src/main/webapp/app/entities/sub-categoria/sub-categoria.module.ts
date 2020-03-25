import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiuconSharedModule } from 'app/shared/shared.module';
import { SubCategoriaComponent } from './sub-categoria.component';
import { SubCategoriaDetailComponent } from './sub-categoria-detail.component';
import { SubCategoriaUpdateComponent } from './sub-categoria-update.component';
import { SubCategoriaDeleteDialogComponent } from './sub-categoria-delete-dialog.component';
import { subCategoriaRoute } from './sub-categoria.route';

@NgModule({
  imports: [DiuconSharedModule, RouterModule.forChild(subCategoriaRoute)],
  declarations: [SubCategoriaComponent, SubCategoriaDetailComponent, SubCategoriaUpdateComponent, SubCategoriaDeleteDialogComponent],
  entryComponents: [SubCategoriaDeleteDialogComponent]
})
export class DiuconSubCategoriaModule {}
