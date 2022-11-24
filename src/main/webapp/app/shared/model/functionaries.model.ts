import { IRegistration } from '@/shared/model/registration.model';

export interface IFunctionaries {
  id?: number;
  setor?: string | null;
  registration?: IRegistration | null;
}

export class Functionaries implements IFunctionaries {
  constructor(public id?: number, public setor?: string | null, public registration?: IRegistration | null) {}
}
