import { Moment } from 'moment';
import { IComentario } from 'app/shared/model/comentario.model';
import { IHistorialEstado } from 'app/shared/model/historial-estado.model';
import { Estado } from 'app/shared/model/enumerations/estado.model';

export interface IIncidente {
  id?: number;
  fecha?: Moment;
  resumen?: string;
  descripcion?: any;
  estado?: Estado;
  localizacion?: string;
  latitud?: number;
  longitud?: number;
  fechaResolucion?: Moment;
  fechaCierre?: Moment;
  email?: string;
  comentarios?: IComentario[];
  historials?: IHistorialEstado[];
  categoriaNombre?: string;
  categoriaId?: number;
  subcategoriaNombre?: string;
  subcategoriaId?: number;
  operadorLogin?: string;
  operadorId?: number;
  responsableNombreCompleto?: string;
  responsableId?: number;
}

export class Incidente implements IIncidente {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public resumen?: string,
    public descripcion?: any,
    public estado?: Estado,
    public localizacion?: string,
    public latitud?: number,
    public longitud?: number,
    public fechaResolucion?: Moment,
    public fechaCierre?: Moment,
    public email?: string,
    public comentarios?: IComentario[],
    public historials?: IHistorialEstado[],
    public categoriaNombre?: string,
    public categoriaId?: number,
    public subcategoriaNombre?: string,
    public subcategoriaId?: number,
    public operadorLogin?: string,
    public operadorId?: number,
    public responsableNombreCompleto?: string,
    public responsableId?: number
  ) {}
}
