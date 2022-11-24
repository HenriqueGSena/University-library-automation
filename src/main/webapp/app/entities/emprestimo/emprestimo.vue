<template>
  <div>
    <h2 id="page-heading" data-cy="EmprestimoHeading">
      <span id="emprestimo-heading">Emprestimos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'EmprestimoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-emprestimo"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Emprestimo </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && emprestimos && emprestimos.length === 0">
      <span>No emprestimos found</span>
    </div>
    <div class="table-responsive" v-if="emprestimos && emprestimos.length > 0">
      <table class="table table-striped" aria-describedby="emprestimos">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Publications</span></th>
            <th scope="row"><span>Registration</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="emprestimo in emprestimos" :key="emprestimo.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EmprestimoView', params: { emprestimoId: emprestimo.id } }">{{ emprestimo.id }}</router-link>
            </td>
            <td>
              <span v-for="(publications, i) in emprestimo.publications" :key="publications.id"
                >{{ i > 0 ? ', ' : '' }}
                <router-link class="form-control-static" :to="{ name: 'PublicationsView', params: { publicationsId: publications.id } }">{{
                  publications.id
                }}</router-link>
              </span>
            </td>
            <td>
              <span v-for="(registration, i) in emprestimo.registrations" :key="registration.id"
                >{{ i > 0 ? ', ' : '' }}
                <router-link class="form-control-static" :to="{ name: 'RegistrationView', params: { registrationId: registration.id } }">{{
                  registration.id
                }}</router-link>
              </span>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EmprestimoView', params: { emprestimoId: emprestimo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EmprestimoEdit', params: { emprestimoId: emprestimo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(emprestimo)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="universityAutomationApp.emprestimo.delete.question" data-cy="emprestimoDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-emprestimo-heading">Are you sure you want to delete this Emprestimo?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-emprestimo"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeEmprestimo()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./emprestimo.component.ts"></script>
