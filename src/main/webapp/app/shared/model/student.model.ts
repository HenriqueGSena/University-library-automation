import { IRegistration } from '@/shared/model/registration.model';

export interface IStudent {
  id?: number;
  curso?: string | null;
  registration?: IRegistration | null;
}

export class Student implements IStudent {
  constructor(public id?: number, public curso?: string | null, public registration?: IRegistration | null) {}
}
