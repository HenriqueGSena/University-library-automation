/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PublicationsUpdateComponent from '@/entities/publications/publications-update.vue';
import PublicationsClass from '@/entities/publications/publications-update.component';
import PublicationsService from '@/entities/publications/publications.service';

import BookService from '@/entities/book/book.service';

import PeriodicalsService from '@/entities/periodicals/periodicals.service';

import EmprestimoService from '@/entities/emprestimo/emprestimo.service';
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
  describe('Publications Management Update Component', () => {
    let wrapper: Wrapper<PublicationsClass>;
    let comp: PublicationsClass;
    let publicationsServiceStub: SinonStubbedInstance<PublicationsService>;

    beforeEach(() => {
      publicationsServiceStub = sinon.createStubInstance<PublicationsService>(PublicationsService);

      wrapper = shallowMount<PublicationsClass>(PublicationsUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          publicationsService: () => publicationsServiceStub,
          alertService: () => new AlertService(),

          bookService: () =>
            sinon.createStubInstance<BookService>(BookService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          periodicalsService: () =>
            sinon.createStubInstance<PeriodicalsService>(PeriodicalsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          emprestimoService: () =>
            sinon.createStubInstance<EmprestimoService>(EmprestimoService, {
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
        comp.publications = entity;
        publicationsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(publicationsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.publications = entity;
        publicationsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(publicationsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPublications = { id: 123 };
        publicationsServiceStub.find.resolves(foundPublications);
        publicationsServiceStub.retrieve.resolves([foundPublications]);

        // WHEN
        comp.beforeRouteEnter({ params: { publicationsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.publications).toBe(foundPublications);
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
