import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITeacher } from '@/shared/model/teacher.model';
import TeacherService from './teacher.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TeacherDetails extends Vue {
  @Inject('teacherService') private teacherService: () => TeacherService;
  @Inject('alertService') private alertService: () => AlertService;

  public teacher: ITeacher = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teacherId) {
        vm.retrieveTeacher(to.params.teacherId);
      }
    });
  }

  public retrieveTeacher(teacherId) {
    this.teacherService()
      .find(teacherId)
      .then(res => {
        this.teacher = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
