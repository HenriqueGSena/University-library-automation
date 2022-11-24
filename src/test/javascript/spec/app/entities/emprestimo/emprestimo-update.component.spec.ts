/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import EmprestimoUpdateComponent from '@/entities/emprestimo/emprestimo-update.vue';
import EmprestimoClass from '@/entities/emprestimo/emprestimo-update.component';
import EmprestimoService from '@/entities/emprestimo/emprestimo.service';

import PublicationsService from '@/entities/publications/publications.service';

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
  describe('Emprestimo Management Update Component', () => {
    let wrapper: Wrapper<EmprestimoClass>;
    let comp: EmprestimoClass;
    let emprestimoServiceStub: SinonStubbedInstance<EmprestimoService>;

    beforeEach(() => {
      emprestimoServiceStub = sinon.createStubInstance<EmprestimoService>(EmprestimoService);

      wrapper = shallowMount<EmprestimoClass>(EmprestimoUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          emprestimoService: () => emprestimoServiceStub,
          alertService: () => new AlertService(),

          publicationsService: () =>
            sinon.createStubInstance<PublicationsService>(PublicationsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

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
        comp.emprestimo = entity;
        emprestimoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(emprestimoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.emprestimo = entity;
        emprestimoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(emprestimoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEmprestimo = { id: 123 };
        emprestimoServiceStub.find.resolves(foundEmprestimo);
        emprestimoServiceStub.retrieve.resolves([foundEmprestimo]);

        // WHEN
        comp.beforeRouteEnter({ params: { emprestimoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.emprestimo).toBe(foundEmprestimo);
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
