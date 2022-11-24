import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Book = () => import('@/entities/book/book.vue');
// prettier-ignore
const BookUpdate = () => import('@/entities/book/book-update.vue');
// prettier-ignore
const BookDetails = () => import('@/entities/book/book-details.vue');
// prettier-ignore
const Functionaries = () => import('@/entities/functionaries/functionaries.vue');
// prettier-ignore
const FunctionariesUpdate = () => import('@/entities/functionaries/functionaries-update.vue');
// prettier-ignore
const FunctionariesDetails = () => import('@/entities/functionaries/functionaries-details.vue');
// prettier-ignore
const Librarian = () => import('@/entities/librarian/librarian.vue');
// prettier-ignore
const LibrarianUpdate = () => import('@/entities/librarian/librarian-update.vue');
// prettier-ignore
const LibrarianDetails = () => import('@/entities/librarian/librarian-details.vue');
// prettier-ignore
const Periodicals = () => import('@/entities/periodicals/periodicals.vue');
// prettier-ignore
const PeriodicalsUpdate = () => import('@/entities/periodicals/periodicals-update.vue');
// prettier-ignore
const PeriodicalsDetails = () => import('@/entities/periodicals/periodicals-details.vue');
// prettier-ignore
const Publications = () => import('@/entities/publications/publications.vue');
// prettier-ignore
const PublicationsUpdate = () => import('@/entities/publications/publications-update.vue');
// prettier-ignore
const PublicationsDetails = () => import('@/entities/publications/publications-details.vue');
// prettier-ignore
const Registration = () => import('@/entities/registration/registration.vue');
// prettier-ignore
const RegistrationUpdate = () => import('@/entities/registration/registration-update.vue');
// prettier-ignore
const RegistrationDetails = () => import('@/entities/registration/registration-details.vue');
// prettier-ignore
const Student = () => import('@/entities/student/student.vue');
// prettier-ignore
const StudentUpdate = () => import('@/entities/student/student-update.vue');
// prettier-ignore
const StudentDetails = () => import('@/entities/student/student-details.vue');
// prettier-ignore
const Teacher = () => import('@/entities/teacher/teacher.vue');
// prettier-ignore
const TeacherUpdate = () => import('@/entities/teacher/teacher-update.vue');
// prettier-ignore
const TeacherDetails = () => import('@/entities/teacher/teacher-details.vue');
// prettier-ignore
const Emprestimo = () => import('@/entities/emprestimo/emprestimo.vue');
// prettier-ignore
const EmprestimoUpdate = () => import('@/entities/emprestimo/emprestimo-update.vue');
// prettier-ignore
const EmprestimoDetails = () => import('@/entities/emprestimo/emprestimo-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'book',
      name: 'Book',
      component: Book,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'book/new',
      name: 'BookCreate',
      component: BookUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'book/:bookId/edit',
      name: 'BookEdit',
      component: BookUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'book/:bookId/view',
      name: 'BookView',
      component: BookDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'functionaries',
      name: 'Functionaries',
      component: Functionaries,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'functionaries/new',
      name: 'FunctionariesCreate',
      component: FunctionariesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'functionaries/:functionariesId/edit',
      name: 'FunctionariesEdit',
      component: FunctionariesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'functionaries/:functionariesId/view',
      name: 'FunctionariesView',
      component: FunctionariesDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'librarian',
      name: 'Librarian',
      component: Librarian,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'librarian/new',
      name: 'LibrarianCreate',
      component: LibrarianUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'librarian/:librarianId/edit',
      name: 'LibrarianEdit',
      component: LibrarianUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'librarian/:librarianId/view',
      name: 'LibrarianView',
      component: LibrarianDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'periodicals',
      name: 'Periodicals',
      component: Periodicals,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'periodicals/new',
      name: 'PeriodicalsCreate',
      component: PeriodicalsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'periodicals/:periodicalsId/edit',
      name: 'PeriodicalsEdit',
      component: PeriodicalsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'periodicals/:periodicalsId/view',
      name: 'PeriodicalsView',
      component: PeriodicalsDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'publications',
      name: 'Publications',
      component: Publications,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'publications/new',
      name: 'PublicationsCreate',
      component: PublicationsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'publications/:publicationsId/edit',
      name: 'PublicationsEdit',
      component: PublicationsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'publications/:publicationsId/view',
      name: 'PublicationsView',
      component: PublicationsDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'registration',
      name: 'Registration',
      component: Registration,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'registration/new',
      name: 'RegistrationCreate',
      component: RegistrationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'registration/:registrationId/edit',
      name: 'RegistrationEdit',
      component: RegistrationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'registration/:registrationId/view',
      name: 'RegistrationView',
      component: RegistrationDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student',
      name: 'Student',
      component: Student,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student/new',
      name: 'StudentCreate',
      component: StudentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student/:studentId/edit',
      name: 'StudentEdit',
      component: StudentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student/:studentId/view',
      name: 'StudentView',
      component: StudentDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'teacher',
      name: 'Teacher',
      component: Teacher,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'teacher/new',
      name: 'TeacherCreate',
      component: TeacherUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'teacher/:teacherId/edit',
      name: 'TeacherEdit',
      component: TeacherUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'teacher/:teacherId/view',
      name: 'TeacherView',
      component: TeacherDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'emprestimo',
      name: 'Emprestimo',
      component: Emprestimo,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'emprestimo/new',
      name: 'EmprestimoCreate',
      component: EmprestimoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'emprestimo/:emprestimoId/edit',
      name: 'EmprestimoEdit',
      component: EmprestimoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'emprestimo/:emprestimoId/view',
      name: 'EmprestimoView',
      component: EmprestimoDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
