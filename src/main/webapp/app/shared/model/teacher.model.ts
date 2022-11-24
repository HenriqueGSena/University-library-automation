import { IRegistration } from '@/shared/model/registration.model';

export interface ITeacher {
  id?: number;
  materia?: string | null;
  registration?: IRegistration | null;
}

export class Teacher implements ITeacher {
  constructor(public id?: number, public materia?: string | null, public registration?: IRegistration | null) {}
}
