import { Moment } from 'moment';
import { ISubCategoria } from 'app/shared/model/sub-categoria.model';

export interface IResponsable {
  id?: number;
  nombreCompleto?: string;
  telefono?: string;
  fechaNacimiento?: Moment;
  dni?: number;
  domicilio?: string;
  latitud?: number;
  longitud?: number;
  profesional?: boolean;
  estudiante?: boolean;
  trabajador?: boolean;
  usuarioLogin?: string;
  usuarioId?: number;
  subcategorias?: ISubCategoria[];
}

export class Responsable implements IResponsable {
  constructor(
    public id?: number,
    public nombreCompleto?: string,
    public telefono?: string,
    public fechaNacimiento?: Moment,
    public dni?: number,
    public domicilio?: string,
    public latitud?: number,
    public longitud?: number,
    public profesional?: boolean,
    public estudiante?: boolean,
    public trabajador?: boolean,
    public usuarioLogin?: string,
    public usuarioId?: number,
    public subcategorias?: ISubCategoria[]
  ) {
    this.profesional = this.profesional || false;
    this.estudiante = this.estudiante || false;
    this.trabajador = this.trabajador || false;
  }
}
