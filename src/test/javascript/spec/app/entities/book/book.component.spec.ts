/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import BookComponent from '@/entities/book/book.vue';
import BookClass from '@/entities/book/book.component';
import BookService from '@/entities/book/book.service';
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
  describe('Book Management Component', () => {
    let wrapper: Wrapper<BookClass>;
    let comp: BookClass;
    let bookServiceStub: SinonStubbedInstance<BookService>;

    beforeEach(() => {
      bookServiceStub = sinon.createStubInstance<BookService>(BookService);
      bookServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<BookClass>(BookComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          bookService: () => bookServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      bookServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllBooks();
      await comp.$nextTick();

      // THEN
      expect(bookServiceStub.retrieve.called).toBeTruthy();
      expect(comp.books[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      bookServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(bookServiceStub.retrieve.callCount).toEqual(1);

      comp.removeBook();
      await comp.$nextTick();

      // THEN
      expect(bookServiceStub.delete.called).toBeTruthy();
      expect(bookServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
