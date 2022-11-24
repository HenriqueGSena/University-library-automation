/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import LibrarianComponent from '@/entities/librarian/librarian.vue';
import LibrarianClass from '@/entities/librarian/librarian.component';
import LibrarianService from '@/entities/librarian/librarian.service';
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
  describe('Librarian Management Component', () => {
    let wrapper: Wrapper<LibrarianClass>;
    let comp: LibrarianClass;
    let librarianServiceStub: SinonStubbedInstance<LibrarianService>;

    beforeEach(() => {
      librarianServiceStub = sinon.createStubInstance<LibrarianService>(LibrarianService);
      librarianServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<LibrarianClass>(LibrarianComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          librarianService: () => librarianServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      librarianServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllLibrarians();
      await comp.$nextTick();

      // THEN
      expect(librarianServiceStub.retrieve.called).toBeTruthy();
      expect(comp.librarians[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      librarianServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(librarianServiceStub.retrieve.callCount).toEqual(1);

      comp.removeLibrarian();
      await comp.$nextTick();

      // THEN
      expect(librarianServiceStub.delete.called).toBeTruthy();
      expect(librarianServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
