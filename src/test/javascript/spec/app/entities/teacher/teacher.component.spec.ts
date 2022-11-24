/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TeacherComponent from '@/entities/teacher/teacher.vue';
import TeacherClass from '@/entities/teacher/teacher.component';
import TeacherService from '@/entities/teacher/teacher.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Teacher Management Component', () => {
    let wrapper: Wrapper<TeacherClass>;
    let comp: TeacherClass;
    let teacherServiceStub: SinonStubbedInstance<TeacherService>;

    beforeEach(() => {
      teacherServiceStub = sinon.createStubInstance<TeacherService>(TeacherService);
      teacherServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TeacherClass>(TeacherComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          teacherService: () => teacherServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      teacherServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTeachers();
      await comp.$nextTick();

      // THEN
      expect(teacherServiceStub.retrieve.called).toBeTruthy();
      expect(comp.teachers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      teacherServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(teacherServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTeacher();
      await comp.$nextTick();

      // THEN
      expect(teacherServiceStub.delete.called).toBeTruthy();
      expect(teacherServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
