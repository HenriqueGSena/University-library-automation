import { IRegistration } from '@/shared/model/registration.model';

export interface ILibrarian {
  id?: number;
  email?: string | null;
  registration?: IRegistration | null;
}

export class Librarian implements ILibrarian {
  constructor(public id?: number, public email?: string | null, public registration?: IRegistration | null) {}
}
