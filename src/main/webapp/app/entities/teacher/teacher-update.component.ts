import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import RegistrationService from '@/entities/registration/registration.service';
import { IRegistration } from '@/shared/model/registration.model';

import { ITeacher, Teacher } from '@/shared/model/teacher.model';
import TeacherService from './teacher.service';

const validations: any = {
  teacher: {
    materia: {},
  },
};

@Component({
  validations,
})
export default class TeacherUpdate extends Vue {
  @Inject('teacherService') private teacherService: () => TeacherService;
  @Inject('alertService') private alertService: () => AlertService;

  public teacher: ITeacher = new Teacher();

  @Inject('registrationService') private registrationService: () => RegistrationService;

  public registrations: IRegistration[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teacherId) {
        vm.retrieveTeacher(to.params.teacherId);
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
    if (this.teacher.id) {
      this.teacherService()
        .update(this.teacher)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Teacher is updated with identifier ' + param.id;
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
      this.teacherService()
        .create(this.teacher)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Teacher is created with identifier ' + param.id;
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

  public retrieveTeacher(teacherId): void {
    this.teacherService()
      .find(teacherId)
      .then(res => {
        this.teacher = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.registrationService()
      .retrieve()
      .then(res => {
        this.registrations = res.data;
      });
  }
}
