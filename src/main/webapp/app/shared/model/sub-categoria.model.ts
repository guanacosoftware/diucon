import { IResponsable } from 'app/shared/model/responsable.model';
import { IPlantilla } from 'app/shared/model/plantilla.model';

export interface ISubCategoria {
  id?: number;
  nombre?: string;
  observaciones?: any;
  categoriaNombre?: string;
  categoriaId?: number;
  responsables?: IResponsable[];
  plantillas?: IPlantilla[];
}

export class SubCategoria implements ISubCategoria {
  constructor(
    public id?: number,
    public nombre?: string,
    public observaciones?: any,
    public categoriaNombre?: string,
    public categoriaId?: number,
    public responsables?: IResponsable[],
    public plantillas?: IPlantilla[]
  ) {}
}
