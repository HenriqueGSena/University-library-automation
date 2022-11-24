import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILibrarian } from '@/shared/model/librarian.model';
import LibrarianService from './librarian.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class LibrarianDetails extends Vue {
  @Inject('librarianService') private librarianService: () => LibrarianService;
  @Inject('alertService') private alertService: () => AlertService;

  public librarian: ILibrarian = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.librarianId) {
        vm.retrieveLibrarian(to.params.librarianId);
      }
    });
  }

  public retrieveLibrarian(librarianId) {
    this.librarianService()
      .find(librarianId)
      .then(res => {
        this.librarian = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
