/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PeriodicalsDetailComponent from '@/entities/periodicals/periodicals-details.vue';
import PeriodicalsClass from '@/entities/periodicals/periodicals-details.component';
import PeriodicalsService from '@/entities/periodicals/periodicals.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Periodicals Management Detail Component', () => {
    let wrapper: Wrapper<PeriodicalsClass>;
    let comp: PeriodicalsClass;
    let periodicalsServiceStub: SinonStubbedInstance<PeriodicalsService>;

    beforeEach(() => {
      periodicalsServiceStub = sinon.createStubInstance<PeriodicalsService>(PeriodicalsService);

      wrapper = shallowMount<PeriodicalsClass>(PeriodicalsDetailComponent, {
        store,
        localVue,
        router,
        provide: { periodicalsService: () => periodicalsServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPeriodicals = { id: 123 };
        periodicalsServiceStub.find.resolves(foundPeriodicals);

        // WHEN
        comp.retrievePeriodicals(123);
        await comp.$nextTick();

        // THEN
        expect(comp.periodicals).toBe(foundPeriodicals);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPeriodicals = { id: 123 };
        periodicalsServiceStub.find.resolves(foundPeriodicals);

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
