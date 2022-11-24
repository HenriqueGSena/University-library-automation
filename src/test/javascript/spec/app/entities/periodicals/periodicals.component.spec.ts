/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PeriodicalsComponent from '@/entities/periodicals/periodicals.vue';
import PeriodicalsClass from '@/entities/periodicals/periodicals.component';
import PeriodicalsService from '@/entities/periodicals/periodicals.service';
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
  describe('Periodicals Management Component', () => {
    let wrapper: Wrapper<PeriodicalsClass>;
    let comp: PeriodicalsClass;
    let periodicalsServiceStub: SinonStubbedInstance<PeriodicalsService>;

    beforeEach(() => {
      periodicalsServiceStub = sinon.createStubInstance<PeriodicalsService>(PeriodicalsService);
      periodicalsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PeriodicalsClass>(PeriodicalsComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          periodicalsService: () => periodicalsServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      periodicalsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPeriodicalss();
      await comp.$nextTick();

      // THEN
      expect(periodicalsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.periodicals[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      periodicalsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(periodicalsServiceStub.retrieve.callCount).toEqual(1);

      comp.removePeriodicals();
      await comp.$nextTick();

      // THEN
      expect(periodicalsServiceStub.delete.called).toBeTruthy();
      expect(periodicalsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
