import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFunctionaries } from '@/shared/model/functionaries.model';

import FunctionariesService from './functionaries.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Functionaries extends Vue {
  @Inject('functionariesService') private functionariesService: () => FunctionariesService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public functionaries: IFunctionaries[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllFunctionariess();
  }

  public clear(): void {
    this.retrieveAllFunctionariess();
  }

  public retrieveAllFunctionariess(): void {
    this.isFetching = true;
    this.functionariesService()
      .retrieve()
      .then(
        res => {
          this.functionaries = res.data;
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

  public prepareRemove(instance: IFunctionaries): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeFunctionaries(): void {
    this.functionariesService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Functionaries is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllFunctionariess();
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
