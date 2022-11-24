import { IPublications } from '@/shared/model/publications.model';
import { IRegistration } from '@/shared/model/registration.model';

export interface IEmprestimo {
  id?: number;
  publications?: IPublications[] | null;
  registrations?: IRegistration[] | null;
}

export class Emprestimo implements IEmprestimo {
  constructor(public id?: number, public publications?: IPublications[] | null, public registrations?: IRegistration[] | null) {}
}
