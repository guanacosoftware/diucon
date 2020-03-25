import { Moment } from 'moment';
import { Estado } from 'app/shared/model/enumerations/estado.model';

export interface IHistorialEstado {
  id?: number;
  fecha?: Moment;
  estado?: Estado;
  usuarioLogin?: string;
  usuarioId?: number;
  incidenteId?: number;
}

export class HistorialEstado implements IHistorialEstado {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public estado?: Estado,
    public usuarioLogin?: string,
    public usuarioId?: number,
    public incidenteId?: number
  ) {}
}
