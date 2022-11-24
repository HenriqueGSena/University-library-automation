import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITeacher } from '@/shared/model/teacher.model';

import TeacherService from './teacher.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Teacher extends Vue {
  @Inject('teacherService') private teacherService: () => TeacherService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public teachers: ITeacher[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTeachers();
  }

  public clear(): void {
    this.retrieveAllTeachers();
  }

  public retrieveAllTeachers(): void {
    this.isFetching = true;
    this.teacherService()
      .retrieve()
      .then(
        res => {
          this.teachers = res.data;
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

  public prepareRemove(instance: ITeacher): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTeacher(): void {
    this.teacherService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Teacher is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTeachers();
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
