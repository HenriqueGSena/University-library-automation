import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import PublicationsService from '@/entities/publications/publications.service';
import { IPublications } from '@/shared/model/publications.model';

import { IPeriodicals, Periodicals } from '@/shared/model/periodicals.model';
import PeriodicalsService from './periodicals.service';

const validations: any = {
  periodicals: {
    volume: {},
    numero: {},
    issn: {},
  },
};

@Component({
  validations,
})
export default class PeriodicalsUpdate extends Vue {
  @Inject('periodicalsService') private periodicalsService: () => PeriodicalsService;
  @Inject('alertService') private alertService: () => AlertService;

  public periodicals: IPeriodicals = new Periodicals();

  @Inject('publicationsService') private publicationsService: () => PublicationsService;

  public publications: IPublications[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.periodicalsId) {
        vm.retrievePeriodicals(to.params.periodicalsId);
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
    if (this.periodicals.id) {
      this.periodicalsService()
        .update(this.periodicals)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Periodicals is updated with identifier ' + param.id;
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
      this.periodicalsService()
        .create(this.periodicals)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Periodicals is created with identifier ' + param.id;
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

  public retrievePeriodicals(periodicalsId): void {
    this.periodicalsService()
      .find(periodicalsId)
      .then(res => {
        this.periodicals = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.publicationsService()
      .retrieve()
      .then(res => {
        this.publications = res.data;
      });
  }
}
