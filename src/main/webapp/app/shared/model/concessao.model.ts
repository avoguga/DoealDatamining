import dayjs from 'dayjs';
import { IServidor } from 'app/shared/model/servidor.model';
import { IDiario } from 'app/shared/model/diario.model';

export interface IConcessao {
  id?: number;
  dataAssinatura?: string | null;
  portaria?: string | null;
  periodoAquisitivo?: string | null;
  tempoAfastamento?: number | null;
  dataInicio?: string | null;
  dataFinal?: string | null;
  servidor?: IServidor | null;
  diario?: IDiario | null;
}

export const defaultValue: Readonly<IConcessao> = {};
