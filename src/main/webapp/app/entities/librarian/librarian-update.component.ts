import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import RegistrationService from '@/entities/registration/registration.service';
import { IRegistration } from '@/shared/model/registration.model';

import { ILibrarian, Librarian } from '@/shared/model/librarian.model';
import LibrarianService from './librarian.service';

const validations: any = {
  librarian: {
    email: {},
  },
};

@Component({
  validations,
})
export default class LibrarianUpdate extends Vue {
  @Inject('librarianService') private librarianService: () => LibrarianService;
  @Inject('alertService') private alertService: () => AlertService;

  public librarian: ILibrarian = new Librarian();

  @Inject('registrationService') private registrationService: () => RegistrationService;

  public registrations: IRegistration[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.librarianId) {
        vm.retrieveLibrarian(to.params.librarianId);
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
    if (this.librarian.id) {
      this.librarianService()
        .update(this.librarian)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Librarian is updated with identifier ' + param.id;
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
      this.librarianService()
        .create(this.librarian)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Librarian is created with identifier ' + param.id;
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

  public retrieveLibrarian(librarianId): void {
    this.librarianService()
      .find(librarianId)
      .then(res => {
        this.librarian = res;
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
