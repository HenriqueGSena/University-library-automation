import { IBook } from '@/shared/model/book.model';
import { IPeriodicals } from '@/shared/model/periodicals.model';
import { IEmprestimo } from '@/shared/model/emprestimo.model';

import { Status } from '@/shared/model/enumerations/status.model';
export interface IPublications {
  id?: number;
  titulo?: string | null;
  preco?: number | null;
  dataPublicacao?: string | null;
  status?: Status | null;
  books?: IBook[] | null;
  periodicals?: IPeriodicals[] | null;
  emprestimos?: IEmprestimo[] | null;
}

export class Publications implements IPublications {
  constructor(
    public id?: number,
    public titulo?: string | null,
    public preco?: number | null,
    public dataPublicacao?: string | null,
    public status?: Status | null,
    public books?: IBook[] | null,
    public periodicals?: IPeriodicals[] | null,
    public emprestimos?: IEmprestimo[] | null
  ) {}
}
