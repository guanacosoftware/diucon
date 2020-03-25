import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IResponsable } from 'app/shared/model/responsable.model';

type EntityResponseType = HttpResponse<IResponsable>;
type EntityArrayResponseType = HttpResponse<IResponsable[]>;

@Injectable({ providedIn: 'root' })
export class ResponsableService {
  public resourceUrl = SERVER_API_URL + 'api/responsables';

  constructor(protected http: HttpClient) {}

  create(responsable: IResponsable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsable);
    return this.http
      .post<IResponsable>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(responsable: IResponsable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsable);
    return this.http
      .put<IResponsable>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IResponsable>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IResponsable[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(responsable: IResponsable): IResponsable {
    const copy: IResponsable = Object.assign({}, responsable, {
      fechaNacimiento:
        responsable.fechaNacimiento && responsable.fechaNacimiento.isValid() ? responsable.fechaNacimiento.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaNacimiento = res.body.fechaNacimiento ? moment(res.body.fechaNacimiento) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((responsable: IResponsable) => {
        responsable.fechaNacimiento = responsable.fechaNacimiento ? moment(responsable.fechaNacimiento) : undefined;
      });
    }
    return res;
  }
}
