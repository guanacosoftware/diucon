import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'categoria',
        loadChildren: () => import('./categoria/categoria.module').then(m => m.DiuconCategoriaModule)
      },
      {
        path: 'sub-categoria',
        loadChildren: () => import('./sub-categoria/sub-categoria.module').then(m => m.DiuconSubCategoriaModule)
      },
      {
        path: 'incidente',
        loadChildren: () => import('./incidente/incidente.module').then(m => m.DiuconIncidenteModule)
      },
      {
        path: 'historial-estado',
        loadChildren: () => import('./historial-estado/historial-estado.module').then(m => m.DiuconHistorialEstadoModule)
      },
      {
        path: 'plantilla',
        loadChildren: () => import('./plantilla/plantilla.module').then(m => m.DiuconPlantillaModule)
      },
      {
        path: 'comentario',
        loadChildren: () => import('./comentario/comentario.module').then(m => m.DiuconComentarioModule)
      },
      {
        path: 'responsable',
        loadChildren: () => import('./responsable/responsable.module').then(m => m.DiuconResponsableModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class DiuconEntityModule {}
