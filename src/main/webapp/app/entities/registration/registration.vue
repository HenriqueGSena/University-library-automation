<template>
  <div>
    <h2 id="page-heading" data-cy="RegistrationHeading">
      <span id="registration-heading">Registrations</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'RegistrationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-registration"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Registration </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && registrations && registrations.length === 0">
      <span>No registrations found</span>
    </div>
    <div class="table-responsive" v-if="registrations && registrations.length > 0">
      <table class="table table-striped" aria-describedby="registrations">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Matricula</span></th>
            <th scope="row"><span>Nome</span></th>
            <th scope="row"><span>Data Nascimento</span></th>
            <th scope="row"><span>Endereco</span></th>
            <th scope="row"><span>Telefone</span></th>
            <th scope="row"><span>Email</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="registration in registrations" :key="registration.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RegistrationView', params: { registrationId: registration.id } }">{{
                registration.id
              }}</router-link>
            </td>
            <td>{{ registration.matricula }}</td>
            <td>{{ registration.nome }}</td>
            <td>{{ registration.dataNascimento }}</td>
            <td>{{ registration.endereco }}</td>
            <td>{{ registration.telefone }}</td>
            <td>{{ registration.email }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'RegistrationView', params: { registrationId: registration.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'RegistrationEdit', params: { registrationId: registration.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(registration)"
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
        ><span id="universityAutomationApp.registration.delete.question" data-cy="registrationDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-registration-heading">Are you sure you want to delete this Registration?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-registration"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeRegistration()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./registration.component.ts"></script>
