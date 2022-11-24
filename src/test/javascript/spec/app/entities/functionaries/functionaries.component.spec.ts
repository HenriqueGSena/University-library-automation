/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import FunctionariesComponent from '@/entities/functionaries/functionaries.vue';
import FunctionariesClass from '@/entities/functionaries/functionaries.component';
import FunctionariesService from '@/entities/functionaries/functionaries.service';
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
  describe('Functionaries Management Component', () => {
    let wrapper: Wrapper<FunctionariesClass>;
    let comp: FunctionariesClass;
    let functionariesServiceStub: SinonStubbedInstance<FunctionariesService>;

    beforeEach(() => {
      functionariesServiceStub = sinon.createStubInstance<FunctionariesService>(FunctionariesService);
      functionariesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<FunctionariesClass>(FunctionariesComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          functionariesService: () => functionariesServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      functionariesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllFunctionariess();
      await comp.$nextTick();

      // THEN
      expect(functionariesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.functionaries[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      functionariesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(functionariesServiceStub.retrieve.callCount).toEqual(1);

      comp.removeFunctionaries();
      await comp.$nextTick();

      // THEN
      expect(functionariesServiceStub.delete.called).toBeTruthy();
      expect(functionariesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
