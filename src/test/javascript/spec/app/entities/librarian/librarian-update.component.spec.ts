/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import LibrarianUpdateComponent from '@/entities/librarian/librarian-update.vue';
import LibrarianClass from '@/entities/librarian/librarian-update.component';
import LibrarianService from '@/entities/librarian/librarian.service';

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
  describe('Librarian Management Update Component', () => {
    let wrapper: Wrapper<LibrarianClass>;
    let comp: LibrarianClass;
    let librarianServiceStub: SinonStubbedInstance<LibrarianService>;

    beforeEach(() => {
      librarianServiceStub = sinon.createStubInstance<LibrarianService>(LibrarianService);

      wrapper = shallowMount<LibrarianClass>(LibrarianUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          librarianService: () => librarianServiceStub,
          alertService: () => new AlertService(),

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
        comp.librarian = entity;
        librarianServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(librarianServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.librarian = entity;
        librarianServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(librarianServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLibrarian = { id: 123 };
        librarianServiceStub.find.resolves(foundLibrarian);
        librarianServiceStub.retrieve.resolves([foundLibrarian]);

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
