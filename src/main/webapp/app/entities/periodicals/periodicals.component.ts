import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPeriodicals } from '@/shared/model/periodicals.model';

import PeriodicalsService from './periodicals.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Periodicals extends Vue {
  @Inject('periodicalsService') private periodicalsService: () => PeriodicalsService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public periodicals: IPeriodicals[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPeriodicalss();
  }

  public clear(): void {
    this.retrieveAllPeriodicalss();
  }

  public retrieveAllPeriodicalss(): void {
    this.isFetching = true;
    this.periodicalsService()
      .retrieve()
      .then(
        res => {
          this.periodicals = res.data;
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

  public prepareRemove(instance: IPeriodicals): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePeriodicals(): void {
    this.periodicalsService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Periodicals is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPeriodicalss();
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
