/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FunctionariesDetailComponent from '@/entities/functionaries/functionaries-details.vue';
import FunctionariesClass from '@/entities/functionaries/functionaries-details.component';
import FunctionariesService from '@/entities/functionaries/functionaries.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Functionaries Management Detail Component', () => {
    let wrapper: Wrapper<FunctionariesClass>;
    let comp: FunctionariesClass;
    let functionariesServiceStub: SinonStubbedInstance<FunctionariesService>;

    beforeEach(() => {
      functionariesServiceStub = sinon.createStubInstance<FunctionariesService>(FunctionariesService);

      wrapper = shallowMount<FunctionariesClass>(FunctionariesDetailComponent, {
        store,
        localVue,
        router,
        provide: { functionariesService: () => functionariesServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFunctionaries = { id: 123 };
        functionariesServiceStub.find.resolves(foundFunctionaries);

        // WHEN
        comp.retrieveFunctionaries(123);
        await comp.$nextTick();

        // THEN
        expect(comp.functionaries).toBe(foundFunctionaries);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFunctionaries = { id: 123 };
        functionariesServiceStub.find.resolves(foundFunctionaries);

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
