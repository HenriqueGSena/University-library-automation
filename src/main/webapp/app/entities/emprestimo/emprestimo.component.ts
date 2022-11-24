import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEmprestimo } from '@/shared/model/emprestimo.model';

import EmprestimoService from './emprestimo.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Emprestimo extends Vue {
  @Inject('emprestimoService') private emprestimoService: () => EmprestimoService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public emprestimos: IEmprestimo[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllEmprestimos();
  }

  public clear(): void {
    this.retrieveAllEmprestimos();
  }

  public retrieveAllEmprestimos(): void {
    this.isFetching = true;
    this.emprestimoService()
      .retrieve()
      .then(
        res => {
          this.emprestimos = res.data;
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

  public prepareRemove(instance: IEmprestimo): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeEmprestimo(): void {
    this.emprestimoService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Emprestimo is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllEmprestimos();
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
