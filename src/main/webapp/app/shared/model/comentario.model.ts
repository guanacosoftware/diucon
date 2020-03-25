export interface IComentario {
  id?: number;
  cuerpo?: any;
  usuarioLogin?: string;
  usuarioId?: number;
  incidenteId?: number;
}

export class Comentario implements IComentario {
  constructor(
    public id?: number,
    public cuerpo?: any,
    public usuarioLogin?: string,
    public usuarioId?: number,
    public incidenteId?: number
  ) {}
}
