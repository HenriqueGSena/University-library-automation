/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RegistrationComponent from '@/entities/registration/registration.vue';
import RegistrationClass from '@/entities/registration/registration.component';
import RegistrationService from '@/entities/registration/registration.service';
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
  describe('Registration Management Component', () => {
    let wrapper: Wrapper<RegistrationClass>;
    let comp: RegistrationClass;
    let registrationServiceStub: SinonStubbedInstance<RegistrationService>;

    beforeEach(() => {
      registrationServiceStub = sinon.createStubInstance<RegistrationService>(RegistrationService);
      registrationServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RegistrationClass>(RegistrationComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          registrationService: () => registrationServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      registrationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllRegistrations();
      await comp.$nextTick();

      // THEN
      expect(registrationServiceStub.retrieve.called).toBeTruthy();
      expect(comp.registrations[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      registrationServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(registrationServiceStub.retrieve.callCount).toEqual(1);

      comp.removeRegistration();
      await comp.$nextTick();

      // THEN
      expect(registrationServiceStub.delete.called).toBeTruthy();
      expect(registrationServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
