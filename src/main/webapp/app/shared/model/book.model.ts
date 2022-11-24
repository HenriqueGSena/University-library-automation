import { IPublications } from '@/shared/model/publications.model';

export interface IBook {
  id?: number;
  autor?: string | null;
  isbn?: number | null;
  editora?: string | null;
  edicao?: string | null;
  publications?: IPublications | null;
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public autor?: string | null,
    public isbn?: number | null,
    public editora?: string | null,
    public edicao?: string | null,
    public publications?: IPublications | null
  ) {}
}
