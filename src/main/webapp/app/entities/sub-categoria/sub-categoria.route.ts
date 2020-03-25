import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISubCategoria, SubCategoria } from 'app/shared/model/sub-categoria.model';
import { SubCategoriaService } from './sub-categoria.service';
import { SubCategoriaComponent } from './sub-categoria.component';
import { SubCategoriaDetailComponent } from './sub-categoria-detail.component';
import { SubCategoriaUpdateComponent } from './sub-categoria-update.component';

@Injectable({ providedIn: 'root' })
export class SubCategoriaResolve implements Resolve<ISubCategoria> {
  constructor(private service: SubCategoriaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubCategoria> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((subCategoria: HttpResponse<SubCategoria>) => {
          if (subCategoria.body) {
            return of(subCategoria.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubCategoria());
  }
}

export const subCategoriaRoute: Routes = [
  {
    path: '',
    component: SubCategoriaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.subCategoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SubCategoriaDetailComponent,
    resolve: {
      subCategoria: SubCategoriaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.subCategoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SubCategoriaUpdateComponent,
    resolve: {
      subCategoria: SubCategoriaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.subCategoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SubCategoriaUpdateComponent,
    resolve: {
      subCategoria: SubCategoriaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.subCategoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
