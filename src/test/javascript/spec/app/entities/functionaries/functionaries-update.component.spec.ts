/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import FunctionariesUpdateComponent from '@/entities/functionaries/functionaries-update.vue';
import FunctionariesClass from '@/entities/functionaries/functionaries-update.component';
import FunctionariesService from '@/entities/functionaries/functionaries.service';

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
  describe('Functionaries Management Update Component', () => {
    let wrapper: Wrapper<FunctionariesClass>;
    let comp: FunctionariesClass;
    let functionariesServiceStub: SinonStubbedInstance<FunctionariesService>;

    beforeEach(() => {
      functionariesServiceStub = sinon.createStubInstance<FunctionariesService>(FunctionariesService);

      wrapper = shallowMount<FunctionariesClass>(FunctionariesUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          functionariesService: () => functionariesServiceStub,
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
        comp.functionaries = entity;
        functionariesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(functionariesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.functionaries = entity;
        functionariesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(functionariesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFunctionaries = { id: 123 };
        functionariesServiceStub.find.resolves(foundFunctionaries);
        functionariesServiceStub.retrieve.resolves([foundFunctionaries]);

        // WHEN
        comp.beforeRouteEnter({ params: { functionariesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.functionaries).toBe(foundFunctionaries);
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
