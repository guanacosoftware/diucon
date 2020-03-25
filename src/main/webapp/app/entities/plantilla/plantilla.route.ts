import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlantilla, Plantilla } from 'app/shared/model/plantilla.model';
import { PlantillaService } from './plantilla.service';
import { PlantillaComponent } from './plantilla.component';
import { PlantillaDetailComponent } from './plantilla-detail.component';
import { PlantillaUpdateComponent } from './plantilla-update.component';

@Injectable({ providedIn: 'root' })
export class PlantillaResolve implements Resolve<IPlantilla> {
  constructor(private service: PlantillaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlantilla> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((plantilla: HttpResponse<Plantilla>) => {
          if (plantilla.body) {
            return of(plantilla.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Plantilla());
  }
}

export const plantillaRoute: Routes = [
  {
    path: '',
    component: PlantillaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.plantilla.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlantillaDetailComponent,
    resolve: {
      plantilla: PlantillaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.plantilla.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlantillaUpdateComponent,
    resolve: {
      plantilla: PlantillaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.plantilla.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlantillaUpdateComponent,
    resolve: {
      plantilla: PlantillaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'diuconApp.plantilla.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
