<template>
  <div>
    <h2 id="page-heading" data-cy="PublicationsHeading">
      <span id="publications-heading">Publications</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'PublicationsCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-publications"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Publications </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && publications && publications.length === 0">
      <span>No publications found</span>
    </div>
    <div class="table-responsive" v-if="publications && publications.length > 0">
      <table class="table table-striped" aria-describedby="publications">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Titulo</span></th>
            <th scope="row"><span>Preco</span></th>
            <th scope="row"><span>Data Publicacao</span></th>
            <th scope="row"><span>Status</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="publications in publications" :key="publications.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PublicationsView', params: { publicationsId: publications.id } }">{{
                publications.id
              }}</router-link>
            </td>
            <td>{{ publications.titulo }}</td>
            <td>{{ publications.preco }}</td>
            <td>{{ publications.dataPublicacao }}</td>
            <td>{{ publications.status }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PublicationsView', params: { publicationsId: publications.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PublicationsEdit', params: { publicationsId: publications.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(publications)"
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
        ><span id="universityAutomationApp.publications.delete.question" data-cy="publicationsDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-publications-heading">Are you sure you want to delete this Publications?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-publications"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removePublications()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./publications.component.ts"></script>
