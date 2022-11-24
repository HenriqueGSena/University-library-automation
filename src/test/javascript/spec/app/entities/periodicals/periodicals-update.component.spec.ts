/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PeriodicalsUpdateComponent from '@/entities/periodicals/periodicals-update.vue';
import PeriodicalsClass from '@/entities/periodicals/periodicals-update.component';
import PeriodicalsService from '@/entities/periodicals/periodicals.service';

import PublicationsService from '@/entities/publications/publications.service';
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
  describe('Periodicals Management Update Component', () => {
    let wrapper: Wrapper<PeriodicalsClass>;
    let comp: PeriodicalsClass;
    let periodicalsServiceStub: SinonStubbedInstance<PeriodicalsService>;

    beforeEach(() => {
      periodicalsServiceStub = sinon.createStubInstance<PeriodicalsService>(PeriodicalsService);

      wrapper = shallowMount<PeriodicalsClass>(PeriodicalsUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          periodicalsService: () => periodicalsServiceStub,
          alertService: () => new AlertService(),

          publicationsService: () =>
            sinon.createStubInstance<PublicationsService>(PublicationsService, {
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
        comp.periodicals = entity;
        periodicalsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(periodicalsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.periodicals = entity;
        periodicalsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(periodicalsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPeriodicals = { id: 123 };
        periodicalsServiceStub.find.resolves(foundPeriodicals);
        periodicalsServiceStub.retrieve.resolves([foundPeriodicals]);

        // WHEN
        comp.beforeRouteEnter({ params: { periodicalsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.periodicals).toBe(foundPeriodicals);
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
