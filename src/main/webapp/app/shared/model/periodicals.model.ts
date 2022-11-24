import { IPublications } from '@/shared/model/publications.model';

export interface IPeriodicals {
  id?: number;
  volume?: number | null;
  numero?: number | null;
  issn?: string | null;
  publications?: IPublications | null;
}

export class Periodicals implements IPeriodicals {
  constructor(
    public id?: number,
    public volume?: number | null,
    public numero?: number | null,
    public issn?: string | null,
    public publications?: IPublications | null
  ) {}
}
