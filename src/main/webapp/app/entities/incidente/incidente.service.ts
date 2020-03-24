import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIncidente } from 'app/shared/model/incidente.model';

type EntityResponseType = HttpResponse<IIncidente>;
type EntityArrayResponseType = HttpResponse<IIncidente[]>;

@Injectable({ providedIn: 'root' })
export class IncidenteService {
  public resourceUrl = SERVER_API_URL + 'api/incidentes';

  constructor(protected http: HttpClient) {}

  create(incidente: IIncidente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(incidente);
    return this.http
      .post<IIncidente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(incidente: IIncidente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(incidente);
    return this.http
      .put<IIncidente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIncidente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIncidente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(incidente: IIncidente): IIncidente {
    const copy: IIncidente = Object.assign({}, incidente, {
      fecha: incidente.fecha && incidente.fecha.isValid() ? incidente.fecha.toJSON() : undefined,
      fechaResolucion:
        incidente.fechaResolucion && incidente.fechaResolucion.isValid() ? incidente.fechaResolucion.format(DATE_FORMAT) : undefined,
      fechaCierre: incidente.fechaCierre && incidente.fechaCierre.isValid() ? incidente.fechaCierre.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha ? moment(res.body.fecha) : undefined;
      res.body.fechaResolucion = res.body.fechaResolucion ? moment(res.body.fechaResolucion) : undefined;
      res.body.fechaCierre = res.body.fechaCierre ? moment(res.body.fechaCierre) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((incidente: IIncidente) => {
        incidente.fecha = incidente.fecha ? moment(incidente.fecha) : undefined;
        incidente.fechaResolucion = incidente.fechaResolucion ? moment(incidente.fechaResolucion) : undefined;
        incidente.fechaCierre = incidente.fechaCierre ? moment(incidente.fechaCierre) : undefined;
      });
    }
    return res;
  }
}
