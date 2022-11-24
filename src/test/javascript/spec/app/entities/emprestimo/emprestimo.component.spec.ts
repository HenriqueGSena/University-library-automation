/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import EmprestimoComponent from '@/entities/emprestimo/emprestimo.vue';
import EmprestimoClass from '@/entities/emprestimo/emprestimo.component';
import EmprestimoService from '@/entities/emprestimo/emprestimo.service';
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
  describe('Emprestimo Management Component', () => {
    let wrapper: Wrapper<EmprestimoClass>;
    let comp: EmprestimoClass;
    let emprestimoServiceStub: SinonStubbedInstance<EmprestimoService>;

    beforeEach(() => {
      emprestimoServiceStub = sinon.createStubInstance<EmprestimoService>(EmprestimoService);
      emprestimoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EmprestimoClass>(EmprestimoComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          emprestimoService: () => emprestimoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      emprestimoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEmprestimos();
      await comp.$nextTick();

      // THEN
      expect(emprestimoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.emprestimos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      emprestimoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(emprestimoServiceStub.retrieve.callCount).toEqual(1);

      comp.removeEmprestimo();
      await comp.$nextTick();

      // THEN
      expect(emprestimoServiceStub.delete.called).toBeTruthy();
      expect(emprestimoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
