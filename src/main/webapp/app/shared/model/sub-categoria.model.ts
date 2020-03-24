import { IResponsable } from 'app/shared/model/responsable.model';
import { IPlantilla } from 'app/shared/model/plantilla.model';

export interface ISubCategoria {
  id?: number;
  nombre?: string;
  observaciones?: any;
  categiaNombre?: string;
  categiaId?: number;
  responsables?: IResponsable[];
  plantillas?: IPlantilla[];
}

export class SubCategoria implements ISubCategoria {
  constructor(
    public id?: number,
    public nombre?: string,
    public observaciones?: any,
    public categiaNombre?: string,
    public categiaId?: number,
    public responsables?: IResponsable[],
    public plantillas?: IPlantilla[]
  ) {}
}
