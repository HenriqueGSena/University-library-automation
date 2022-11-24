<template>
  <div>
    <h2 id="page-heading" data-cy="PeriodicalsHeading">
      <span id="periodicals-heading">Periodicals</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'PeriodicalsCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-periodicals"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Periodicals </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && periodicals && periodicals.length === 0">
      <span>No periodicals found</span>
    </div>
    <div class="table-responsive" v-if="periodicals && periodicals.length > 0">
      <table class="table table-striped" aria-describedby="periodicals">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Volume</span></th>
            <th scope="row"><span>Numero</span></th>
            <th scope="row"><span>Issn</span></th>
            <th scope="row"><span>Publications</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="periodicals in periodicals" :key="periodicals.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PeriodicalsView', params: { periodicalsId: periodicals.id } }">{{ periodicals.id }}</router-link>
            </td>
            <td>{{ periodicals.volume }}</td>
            <td>{{ periodicals.numero }}</td>
            <td>{{ periodicals.issn }}</td>
            <td>
              <div v-if="periodicals.publications">
                <router-link :to="{ name: 'PublicationsView', params: { publicationsId: periodicals.publications.id } }">{{
                  periodicals.publications.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PeriodicalsView', params: { periodicalsId: periodicals.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PeriodicalsEdit', params: { periodicalsId: periodicals.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(periodicals)"
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
        ><span id="universityAutomationApp.periodicals.delete.question" data-cy="periodicalsDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-periodicals-heading">Are you sure you want to delete this Periodicals?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-periodicals"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removePeriodicals()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./periodicals.component.ts"></script>
