import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPeriodicals } from '@/shared/model/periodicals.model';
import PeriodicalsService from './periodicals.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PeriodicalsDetails extends Vue {
  @Inject('periodicalsService') private periodicalsService: () => PeriodicalsService;
  @Inject('alertService') private alertService: () => AlertService;

  public periodicals: IPeriodicals = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.periodicalsId) {
        vm.retrievePeriodicals(to.params.periodicalsId);
      }
    });
  }

  public retrievePeriodicals(periodicalsId) {
    this.periodicalsService()
      .find(periodicalsId)
      .then(res => {
        this.periodicals = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
