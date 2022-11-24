import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import BookService from '@/entities/book/book.service';
import { IBook } from '@/shared/model/book.model';

import PeriodicalsService from '@/entities/periodicals/periodicals.service';
import { IPeriodicals } from '@/shared/model/periodicals.model';

import EmprestimoService from '@/entities/emprestimo/emprestimo.service';
import { IEmprestimo } from '@/shared/model/emprestimo.model';

import { IPublications, Publications } from '@/shared/model/publications.model';
import PublicationsService from './publications.service';
import { Status } from '@/shared/model/enumerations/status.model';

const validations: any = {
  publications: {
    titulo: {},
    preco: {},
    dataPublicacao: {},
    status: {},
  },
};

@Component({
  validations,
})
export default class PublicationsUpdate extends Vue {
  @Inject('publicationsService') private publicationsService: () => PublicationsService;
  @Inject('alertService') private alertService: () => AlertService;

  public publications: IPublications = new Publications();

  @Inject('bookService') private bookService: () => BookService;

  public books: IBook[] = [];

  @Inject('periodicalsService') private periodicalsService: () => PeriodicalsService;

  public periodicals: IPeriodicals[] = [];

  @Inject('emprestimoService') private emprestimoService: () => EmprestimoService;

  public emprestimos: IEmprestimo[] = [];
  public statusValues: string[] = Object.keys(Status);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publicationsId) {
        vm.retrievePublications(to.params.publicationsId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.publications.id) {
      this.publicationsService()
        .update(this.publications)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Publications is updated with identifier ' + param.id;
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.publicationsService()
        .create(this.publications)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Publications is created with identifier ' + param.id;
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrievePublications(publicationsId): void {
    this.publicationsService()
      .find(publicationsId)
      .then(res => {
        this.publications = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.bookService()
      .retrieve()
      .then(res => {
        this.books = res.data;
      });
    this.periodicalsService()
      .retrieve()
      .then(res => {
        this.periodicals = res.data;
      });
    this.emprestimoService()
      .retrieve()
      .then(res => {
        this.emprestimos = res.data;
      });
  }
}
