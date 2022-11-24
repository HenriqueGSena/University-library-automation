import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFunctionaries } from '@/shared/model/functionaries.model';
import FunctionariesService from './functionaries.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class FunctionariesDetails extends Vue {
  @Inject('functionariesService') private functionariesService: () => FunctionariesService;
  @Inject('alertService') private alertService: () => AlertService;

  public functionaries: IFunctionaries = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.functionariesId) {
        vm.retrieveFunctionaries(to.params.functionariesId);
      }
    });
  }

  public retrieveFunctionaries(functionariesId) {
    this.functionariesService()
      .find(functionariesId)
      .then(res => {
        this.functionaries = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
