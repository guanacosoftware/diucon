import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Authority } from 'app/shared/constants/authority.constants';
import { IIncidente, Incidente } from 'app/shared/model/incidente.model';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';
import { IncidenteDetailComponent } from './incidente-detail.component';
import { IncidenteUpdateComponent } from './incidente-update.component';
import { IncidenteComponent } from './incidente.component';
import { IncidenteService } from './incidente.service';

@Injectable({ providedIn: 'root' })
export class IncidenteResolve implements Resolve<IIncidente> {
  constructor(private service: IncidenteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIncidente> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((incidente: HttpResponse<Incidente>) => {
          if (incidente.body) {
            return of(incidente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Incidente());
  }
}

export const incidenteRoute: Routes = [
  {
    path: '',
    component: IncidenteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,desc',
      pageTitle: 'diuconApp.incidente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IncidenteDetailComponent,
    resolve: {
      incidente: IncidenteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.incidente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IncidenteUpdateComponent,
    resolve: {
      incidente: IncidenteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.incidente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IncidenteUpdateComponent,
    resolve: {
      incidente: IncidenteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.incidente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
