import { ISubCategoria } from 'app/shared/model/sub-categoria.model';

export interface IPlantilla {
  id?: number;
  nombre?: string;
  cuerpo?: any;
  subcategorias?: ISubCategoria[];
}

export class Plantilla implements IPlantilla {
  constructor(public id?: number, public nombre?: string, public cuerpo?: any, public subcategorias?: ISubCategoria[]) {}
}
