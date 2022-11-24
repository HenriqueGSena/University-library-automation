import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import RegistrationService from '@/entities/registration/registration.service';
import { IRegistration } from '@/shared/model/registration.model';

import { IFunctionaries, Functionaries } from '@/shared/model/functionaries.model';
import FunctionariesService from './functionaries.service';

const validations: any = {
  functionaries: {
    setor: {},
  },
};

@Component({
  validations,
})
export default class FunctionariesUpdate extends Vue {
  @Inject('functionariesService') private functionariesService: () => FunctionariesService;
  @Inject('alertService') private alertService: () => AlertService;

  public functionaries: IFunctionaries = new Functionaries();

  @Inject('registrationService') private registrationService: () => RegistrationService;

  public registrations: IRegistration[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.functionariesId) {
        vm.retrieveFunctionaries(to.params.functionariesId);
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
    if (this.functionaries.id) {
      this.functionariesService()
        .update(this.functionaries)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Functionaries is updated with identifier ' + param.id;
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
      this.functionariesService()
        .create(this.functionaries)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Functionaries is created with identifier ' + param.id;
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

  public retrieveFunctionaries(functionariesId): void {
    this.functionariesService()
      .find(functionariesId)
      .then(res => {
        this.functionaries = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.registrationService()
      .retrieve()
      .then(res => {
        this.registrations = res.data;
      });
  }
}
