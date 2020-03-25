import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHistorialEstado } from 'app/shared/model/historial-estado.model';

type EntityResponseType = HttpResponse<IHistorialEstado>;
type EntityArrayResponseType = HttpResponse<IHistorialEstado[]>;

@Injectable({ providedIn: 'root' })
export class HistorialEstadoService {
  public resourceUrl = SERVER_API_URL + 'api/historial-estados';

  constructor(protected http: HttpClient) {}

  create(historialEstado: IHistorialEstado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historialEstado);
    return this.http
      .post<IHistorialEstado>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(historialEstado: IHistorialEstado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historialEstado);
    return this.http
      .put<IHistorialEstado>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHistorialEstado>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHistorialEstado[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(historialEstado: IHistorialEstado): IHistorialEstado {
    const copy: IHistorialEstado = Object.assign({}, historialEstado, {
      fecha: historialEstado.fecha && historialEstado.fecha.isValid() ? historialEstado.fecha.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha ? moment(res.body.fecha) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((historialEstado: IHistorialEstado) => {
        historialEstado.fecha = historialEstado.fecha ? moment(historialEstado.fecha) : undefined;
      });
    }
    return res;
  }
}
