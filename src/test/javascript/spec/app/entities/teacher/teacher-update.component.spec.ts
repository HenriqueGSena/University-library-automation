/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TeacherUpdateComponent from '@/entities/teacher/teacher-update.vue';
import TeacherClass from '@/entities/teacher/teacher-update.component';
import TeacherService from '@/entities/teacher/teacher.service';

import RegistrationService from '@/entities/registration/registration.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Teacher Management Update Component', () => {
    let wrapper: Wrapper<TeacherClass>;
    let comp: TeacherClass;
    let teacherServiceStub: SinonStubbedInstance<TeacherService>;

    beforeEach(() => {
      teacherServiceStub = sinon.createStubInstance<TeacherService>(TeacherService);

      wrapper = shallowMount<TeacherClass>(TeacherUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          teacherService: () => teacherServiceStub,
          alertService: () => new AlertService(),

          registrationService: () =>
            sinon.createStubInstance<RegistrationService>(RegistrationService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.teacher = entity;
        teacherServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(teacherServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.teacher = entity;
        teacherServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(teacherServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTeacher = { id: 123 };
        teacherServiceStub.find.resolves(foundTeacher);
        teacherServiceStub.retrieve.resolves([foundTeacher]);

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
