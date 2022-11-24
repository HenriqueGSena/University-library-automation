import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import PublicationsService from '@/entities/publications/publications.service';
import { IPublications } from '@/shared/model/publications.model';

import RegistrationService from '@/entities/registration/registration.service';
import { IRegistration } from '@/shared/model/registration.model';

import { IEmprestimo, Emprestimo } from '@/shared/model/emprestimo.model';
import EmprestimoService from './emprestimo.service';

const validations: any = {
  emprestimo: {},
};

@Component({
  validations,
})
export default class EmprestimoUpdate extends Vue {
  @Inject('emprestimoService') private emprestimoService: () => EmprestimoService;
  @Inject('alertService') private alertService: () => AlertService;

  public emprestimo: IEmprestimo = new Emprestimo();

  @Inject('publicationsService') private publicationsService: () => PublicationsService;

  public publications: IPublications[] = [];

  @Inject('registrationService') private registrationService: () => RegistrationService;

  public registrations: IRegistration[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.emprestimoId) {
        vm.retrieveEmprestimo(to.params.emprestimoId);
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
    this.emprestimo.publications = [];
    this.emprestimo.registrations = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.emprestimo.id) {
      this.emprestimoService()
        .update(this.emprestimo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Emprestimo is updated with identifier ' + param.id;
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
      this.emprestimoService()
        .create(this.emprestimo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Emprestimo is created with identifier ' + param.id;
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

  public retrieveEmprestimo(emprestimoId): void {
    this.emprestimoService()
      .find(emprestimoId)
      .then(res => {
        this.emprestimo = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.publicationsService()
      .retrieve()
      .then(res => {
        this.publications = res.data;
      });
    this.registrationService()
      .retrieve()
      .then(res => {
        this.registrations = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      return selectedVals.find(value => option.id === value.id) ?? option;
    }
    return option;
  }
}
