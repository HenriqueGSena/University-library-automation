<template>
  <div>
    <h2 id="page-heading" data-cy="FunctionariesHeading">
      <span id="functionaries-heading">Functionaries</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'FunctionariesCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-functionaries"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Functionaries </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && functionaries && functionaries.length === 0">
      <span>No functionaries found</span>
    </div>
    <div class="table-responsive" v-if="functionaries && functionaries.length > 0">
      <table class="table table-striped" aria-describedby="functionaries">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Setor</span></th>
            <th scope="row"><span>Registration</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="functionaries in functionaries" :key="functionaries.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FunctionariesView', params: { functionariesId: functionaries.id } }">{{
                functionaries.id
              }}</router-link>
            </td>
            <td>{{ functionaries.setor }}</td>
            <td>
              <div v-if="functionaries.registration">
                <router-link :to="{ name: 'RegistrationView', params: { registrationId: functionaries.registration.id } }">{{
                  functionaries.registration.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'FunctionariesView', params: { functionariesId: functionaries.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'FunctionariesEdit', params: { functionariesId: functionaries.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(functionaries)"
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
        ><span id="universityAutomationApp.functionaries.delete.question" data-cy="functionariesDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-functionaries-heading">Are you sure you want to delete this Functionaries?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-functionaries"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeFunctionaries()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./functionaries.component.ts"></script>
