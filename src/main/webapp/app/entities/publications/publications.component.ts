import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPublications } from '@/shared/model/publications.model';

import PublicationsService from './publications.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Publications extends Vue {
  @Inject('publicationsService') private publicationsService: () => PublicationsService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public publications: IPublications[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPublicationss();
  }

  public clear(): void {
    this.retrieveAllPublicationss();
  }

  public retrieveAllPublicationss(): void {
    this.isFetching = true;
    this.publicationsService()
      .retrieve()
      .then(
        res => {
          this.publications = res.data;
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

  public prepareRemove(instance: IPublications): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePublications(): void {
    this.publicationsService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Publications is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPublicationss();
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
