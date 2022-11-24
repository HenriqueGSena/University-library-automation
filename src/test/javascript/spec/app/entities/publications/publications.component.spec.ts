/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PublicationsComponent from '@/entities/publications/publications.vue';
import PublicationsClass from '@/entities/publications/publications.component';
import PublicationsService from '@/entities/publications/publications.service';
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
  describe('Publications Management Component', () => {
    let wrapper: Wrapper<PublicationsClass>;
    let comp: PublicationsClass;
    let publicationsServiceStub: SinonStubbedInstance<PublicationsService>;

    beforeEach(() => {
      publicationsServiceStub = sinon.createStubInstance<PublicationsService>(PublicationsService);
      publicationsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PublicationsClass>(PublicationsComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          publicationsService: () => publicationsServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      publicationsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPublicationss();
      await comp.$nextTick();

      // THEN
      expect(publicationsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.publications[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      publicationsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(publicationsServiceStub.retrieve.callCount).toEqual(1);

      comp.removePublications();
      await comp.$nextTick();

      // THEN
      expect(publicationsServiceStub.delete.called).toBeTruthy();
      expect(publicationsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
