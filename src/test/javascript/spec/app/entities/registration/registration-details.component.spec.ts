/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RegistrationDetailComponent from '@/entities/registration/registration-details.vue';
import RegistrationClass from '@/entities/registration/registration-details.component';
import RegistrationService from '@/entities/registration/registration.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Registration Management Detail Component', () => {
    let wrapper: Wrapper<RegistrationClass>;
    let comp: RegistrationClass;
    let registrationServiceStub: SinonStubbedInstance<RegistrationService>;

    beforeEach(() => {
      registrationServiceStub = sinon.createStubInstance<RegistrationService>(RegistrationService);

      wrapper = shallowMount<RegistrationClass>(RegistrationDetailComponent, {
        store,
        localVue,
        router,
        provide: { registrationService: () => registrationServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRegistration = { id: 123 };
        registrationServiceStub.find.resolves(foundRegistration);

        // WHEN
        comp.retrieveRegistration(123);
        await comp.$nextTick();

        // THEN
        expect(comp.registration).toBe(foundRegistration);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRegistration = { id: 123 };
        registrationServiceStub.find.resolves(foundRegistration);

        // WHEN
        comp.beforeRouteEnter({ params: { registrationId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.registration).toBe(foundRegistration);
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
