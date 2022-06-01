import { IConcessao } from 'app/shared/model/concessao.model';

export interface IServidor {
  id?: number;
  nome?: string | null;
  cpf?: string | null;
  matricula?: string | null;
  cargo?: string | null;
  concessao?: IConcessao | null;
}

export const defaultValue: Readonly<IServidor> = {};
