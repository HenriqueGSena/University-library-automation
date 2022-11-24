/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PublicationsDetailComponent from '@/entities/publications/publications-details.vue';
import PublicationsClass from '@/entities/publications/publications-details.component';
import PublicationsService from '@/entities/publications/publications.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Publications Management Detail Component', () => {
    let wrapper: Wrapper<PublicationsClass>;
    let comp: PublicationsClass;
    let publicationsServiceStub: SinonStubbedInstance<PublicationsService>;

    beforeEach(() => {
      publicationsServiceStub = sinon.createStubInstance<PublicationsService>(PublicationsService);

      wrapper = shallowMount<PublicationsClass>(PublicationsDetailComponent, {
        store,
        localVue,
        router,
        provide: { publicationsService: () => publicationsServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPublications = { id: 123 };
        publicationsServiceStub.find.resolves(foundPublications);

        // WHEN
        comp.retrievePublications(123);
        await comp.$nextTick();

        // THEN
        expect(comp.publications).toBe(foundPublications);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPublications = { id: 123 };
        publicationsServiceStub.find.resolves(foundPublications);

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
