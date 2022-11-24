import { IFunctionaries } from '@/shared/model/functionaries.model';
import { ILibrarian } from '@/shared/model/librarian.model';
import { IStudent } from '@/shared/model/student.model';
import { ITeacher } from '@/shared/model/teacher.model';
import { IEmprestimo } from '@/shared/model/emprestimo.model';

export interface IRegistration {
  id?: number;
  matricula?: number | null;
  nome?: string | null;
  dataNascimento?: string | null;
  endereco?: string | null;
  telefone?: string | null;
  email?: string | null;
  functionaries?: IFunctionaries[] | null;
  librarians?: ILibrarian[] | null;
  students?: IStudent[] | null;
  teachers?: ITeacher[] | null;
  emprestimos?: IEmprestimo[] | null;
}

export class Registration implements IRegistration {
  constructor(
    public id?: number,
    public matricula?: number | null,
    public nome?: string | null,
    public dataNascimento?: string | null,
    public endereco?: string | null,
    public telefone?: string | null,
    public email?: string | null,
    public functionaries?: IFunctionaries[] | null,
    public librarians?: ILibrarian[] | null,
    public students?: IStudent[] | null,
    public teachers?: ITeacher[] | null,
    public emprestimos?: IEmprestimo[] | null
  ) {}
}
