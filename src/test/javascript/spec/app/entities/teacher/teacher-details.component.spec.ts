/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TeacherDetailComponent from '@/entities/teacher/teacher-details.vue';
import TeacherClass from '@/entities/teacher/teacher-details.component';
import TeacherService from '@/entities/teacher/teacher.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Teacher Management Detail Component', () => {
    let wrapper: Wrapper<TeacherClass>;
    let comp: TeacherClass;
    let teacherServiceStub: SinonStubbedInstance<TeacherService>;

    beforeEach(() => {
      teacherServiceStub = sinon.createStubInstance<TeacherService>(TeacherService);

      wrapper = shallowMount<TeacherClass>(TeacherDetailComponent, {
        store,
        localVue,
        router,
        provide: { teacherService: () => teacherServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTeacher = { id: 123 };
        teacherServiceStub.find.resolves(foundTeacher);

        // WHEN
        comp.retrieveTeacher(123);
        await comp.$nextTick();

        // THEN
        expect(comp.teacher).toBe(foundTeacher);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTeacher = { id: 123 };
        teacherServiceStub.find.resolves(foundTeacher);

        // WHEN
        comp.beforeRouteEnter({ params: { teacherId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.teacher).toBe(foundTeacher);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
