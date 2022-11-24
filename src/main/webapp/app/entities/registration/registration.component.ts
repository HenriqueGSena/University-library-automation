import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRegistration } from '@/shared/model/registration.model';

import RegistrationService from './registration.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Registration extends Vue {
  @Inject('registrationService') private registrationService: () => RegistrationService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public registrations: IRegistration[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRegistrations();
  }

  public clear(): void {
    this.retrieveAllRegistrations();
  }

  public retrieveAllRegistrations(): void {
    this.isFetching = true;
    this.registrationService()
      .retrieve()
      .then(
        res => {
          this.registrations = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IRegistration): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRegistration(): void {
    this.registrationService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Registration is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllRegistrations();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
