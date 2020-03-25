import { ISubCategoria } from 'app/shared/model/sub-categoria.model';

export interface ICategoria {
  id?: number;
  nombre?: string;
  observaciones?: any;
  subcategorias?: ISubCategoria[];
}

export class Categoria implements ICategoria {
  constructor(public id?: number, public nombre?: string, public observaciones?: any, public subcategorias?: ISubCategoria[]) {}
}
