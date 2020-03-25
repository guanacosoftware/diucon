import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlantilla } from 'app/shared/model/plantilla.model';

type EntityResponseType = HttpResponse<IPlantilla>;
type EntityArrayResponseType = HttpResponse<IPlantilla[]>;

@Injectable({ providedIn: 'root' })
export class PlantillaService {
  public resourceUrl = SERVER_API_URL + 'api/plantillas';

  constructor(protected http: HttpClient) {}

  create(plantilla: IPlantilla): Observable<EntityResponseType> {
    return this.http.post<IPlantilla>(this.resourceUrl, plantilla, { observe: 'response' });
  }

  update(plantilla: IPlantilla): Observable<EntityResponseType> {
    return this.http.put<IPlantilla>(this.resourceUrl, plantilla, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlantilla>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlantilla[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
