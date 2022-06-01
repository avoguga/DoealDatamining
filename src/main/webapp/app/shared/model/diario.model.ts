import dayjs from 'dayjs';
import { IConcessao } from 'app/shared/model/concessao.model';

export interface IDiario {
  id?: number;
  dataPublicacao?: string | null;
  ano?: number | null;
  numero?: number | null;
  concessaos?: IConcessao[] | null;
}

export const defaultValue: Readonly<IDiario> = {};
