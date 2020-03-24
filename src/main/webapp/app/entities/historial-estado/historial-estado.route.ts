import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHistorialEstado, HistorialEstado } from 'app/shared/model/historial-estado.model';
import { HistorialEstadoService } from './historial-estado.service';
import { HistorialEstadoComponent } from './historial-estado.component';
import { HistorialEstadoDetailComponent } from './historial-estado-detail.component';
import { HistorialEstadoUpdateComponent } from './historial-estado-update.component';

@Injectable({ providedIn: 'root' })
export class HistorialEstadoResolve implements Resolve<IHistorialEstado> {
  constructor(private service: HistorialEstadoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHistorialEstado> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((historialEstado: HttpResponse<HistorialEstado>) => {
          if (historialEstado.body) {
            return of(historialEstado.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HistorialEstado());
  }
}

export const historialEstadoRoute: Routes = [
  {
    path: '',
    component: HistorialEstadoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.historialEstado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HistorialEstadoDetailComponent,
    resolve: {
      historialEstado: HistorialEstadoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.historialEstado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HistorialEstadoUpdateComponent,
    resolve: {
      historialEstado: HistorialEstadoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.historialEstado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HistorialEstadoUpdateComponent,
    resolve: {
      historialEstado: HistorialEstadoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.historialEstado.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
