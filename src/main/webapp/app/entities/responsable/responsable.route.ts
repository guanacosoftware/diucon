import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IResponsable, Responsable } from 'app/shared/model/responsable.model';
import { ResponsableService } from './responsable.service';
import { ResponsableComponent } from './responsable.component';
import { ResponsableDetailComponent } from './responsable-detail.component';
import { ResponsableUpdateComponent } from './responsable-update.component';

@Injectable({ providedIn: 'root' })
export class ResponsableResolve implements Resolve<IResponsable> {
  constructor(private service: ResponsableService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResponsable> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((responsable: HttpResponse<Responsable>) => {
          if (responsable.body) {
            return of(responsable.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Responsable());
  }
}

export const responsableRoute: Routes = [
  {
    path: '',
    component: ResponsableComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'diuconApp.responsable.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ResponsableDetailComponent,
    resolve: {
      responsable: ResponsableResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.responsable.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ResponsableUpdateComponent,
    resolve: {
      responsable: ResponsableResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.responsable.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ResponsableUpdateComponent,
    resolve: {
      responsable: ResponsableResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.responsable.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
