<template>
  <div>
    <h2 id="page-heading" data-cy="LibrarianHeading">
      <span id="librarian-heading">Librarians</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'LibrarianCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-librarian"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Librarian </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && librarians && librarians.length === 0">
      <span>No librarians found</span>
    </div>
    <div class="table-responsive" v-if="librarians && librarians.length > 0">
      <table class="table table-striped" aria-describedby="librarians">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Email</span></th>
            <th scope="row"><span>Registration</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="librarian in librarians" :key="librarian.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'LibrarianView', params: { librarianId: librarian.id } }">{{ librarian.id }}</router-link>
            </td>
            <td>{{ librarian.email }}</td>
            <td>
              <div v-if="librarian.registration">
                <router-link :to="{ name: 'RegistrationView', params: { registrationId: librarian.registration.id } }">{{
                  librarian.registration.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'LibrarianView', params: { librarianId: librarian.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'LibrarianEdit', params: { librarianId: librarian.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(librarian)"
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
        ><span id="universityAutomationApp.librarian.delete.question" data-cy="librarianDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-librarian-heading">Are you sure you want to delete this Librarian?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-librarian"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeLibrarian()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./librarian.component.ts"></script>
