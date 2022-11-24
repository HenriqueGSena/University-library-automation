/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import LibrarianDetailComponent from '@/entities/librarian/librarian-details.vue';
import LibrarianClass from '@/entities/librarian/librarian-details.component';
import LibrarianService from '@/entities/librarian/librarian.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Librarian Management Detail Component', () => {
    let wrapper: Wrapper<LibrarianClass>;
    let comp: LibrarianClass;
    let librarianServiceStub: SinonStubbedInstance<LibrarianService>;

    beforeEach(() => {
      librarianServiceStub = sinon.createStubInstance<LibrarianService>(LibrarianService);

      wrapper = shallowMount<LibrarianClass>(LibrarianDetailComponent, {
        store,
        localVue,
        router,
        provide: { librarianService: () => librarianServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLibrarian = { id: 123 };
        librarianServiceStub.find.resolves(foundLibrarian);

        // WHEN
        comp.retrieveLibrarian(123);
        await comp.$nextTick();

        // THEN
        expect(comp.librarian).toBe(foundLibrarian);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLibrarian = { id: 123 };
        librarianServiceStub.find.resolves(foundLibrarian);

        // WHEN
        comp.beforeRouteEnter({ params: { librarianId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.librarian).toBe(foundLibrarian);
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
