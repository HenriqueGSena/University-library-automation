import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import FunctionariesService from '@/entities/functionaries/functionaries.service';
import { IFunctionaries } from '@/shared/model/functionaries.model';

import LibrarianService from '@/entities/librarian/librarian.service';
import { ILibrarian } from '@/shared/model/librarian.model';

import StudentService from '@/entities/student/student.service';
import { IStudent } from '@/shared/model/student.model';

import TeacherService from '@/entities/teacher/teacher.service';
import { ITeacher } from '@/shared/model/teacher.model';

import EmprestimoService from '@/entities/emprestimo/emprestimo.service';
import { IEmprestimo } from '@/shared/model/emprestimo.model';

import { IRegistration, Registration } from '@/shared/model/registration.model';
import RegistrationService from './registration.service';

const validations: any = {
  registration: {
    matricula: {},
    nome: {},
    dataNascimento: {},
    endereco: {},
    telefone: {},
    email: {},
  },
};

@Component({
  validations,
})
export default class RegistrationUpdate extends Vue {
  @Inject('registrationService') private registrationService: () => RegistrationService;
  @Inject('alertService') private alertService: () => AlertService;

  public registration: IRegistration = new Registration();

  @Inject('functionariesService') private functionariesService: () => FunctionariesService;

  public functionaries: IFunctionaries[] = [];

  @Inject('librarianService') private librarianService: () => LibrarianService;

  public librarians: ILibrarian[] = [];

  @Inject('studentService') private studentService: () => StudentService;

  public students: IStudent[] = [];

  @Inject('teacherService') private teacherService: () => TeacherService;

  public teachers: ITeacher[] = [];

  @Inject('emprestimoService') private emprestimoService: () => EmprestimoService;

  public emprestimos: IEmprestimo[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.registrationId) {
        vm.retrieveRegistration(to.params.registrationId);
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
    if (this.registration.id) {
      this.registrationService()
        .update(this.registration)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Registration is updated with identifier ' + param.id;
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
      this.registrationService()
        .create(this.registration)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Registration is created with identifier ' + param.id;
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

  public retrieveRegistration(registrationId): void {
    this.registrationService()
      .find(registrationId)
      .then(res => {
        this.registration = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.functionariesService()
      .retrieve()
      .then(res => {
        this.functionaries = res.data;
      });
    this.librarianService()
      .retrieve()
      .then(res => {
        this.librarians = res.data;
      });
    this.studentService()
      .retrieve()
      .then(res => {
        this.students = res.data;
      });
    this.teacherService()
      .retrieve()
      .then(res => {
        this.teachers = res.data;
      });
    this.emprestimoService()
      .retrieve()
      .then(res => {
        this.emprestimos = res.data;
      });
  }
}
