import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import BookService from './book/book.service';
import FunctionariesService from './functionaries/functionaries.service';
import LibrarianService from './librarian/librarian.service';
import PeriodicalsService from './periodicals/periodicals.service';
import PublicationsService from './publications/publications.service';
import RegistrationService from './registration/registration.service';
import StudentService from './student/student.service';
import TeacherService from './teacher/teacher.service';
import EmprestimoService from './emprestimo/emprestimo.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('bookService') private bookService = () => new BookService();
  @Provide('functionariesService') private functionariesService = () => new FunctionariesService();
  @Provide('librarianService') private librarianService = () => new LibrarianService();
  @Provide('periodicalsService') private periodicalsService = () => new PeriodicalsService();
  @Provide('publicationsService') private publicationsService = () => new PublicationsService();
  @Provide('registrationService') private registrationService = () => new RegistrationService();
  @Provide('studentService') private studentService = () => new StudentService();
  @Provide('teacherService') private teacherService = () => new TeacherService();
  @Provide('emprestimoService') private emprestimoService = () => new EmprestimoService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
