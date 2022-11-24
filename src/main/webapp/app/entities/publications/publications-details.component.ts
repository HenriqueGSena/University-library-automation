import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPublications } from '@/shared/model/publications.model';
import PublicationsService from './publications.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PublicationsDetails extends Vue {
  @Inject('publicationsService') private publicationsService: () => PublicationsService;
  @Inject('alertService') private alertService: () => AlertService;

  public publications: IPublications = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publicationsId) {
        vm.retrievePublications(to.params.publicationsId);
      }
    });
  }

  public retrievePublications(publicationsId) {
    this.publicationsService()
      .find(publicationsId)
      .then(res => {
        this.publications = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
