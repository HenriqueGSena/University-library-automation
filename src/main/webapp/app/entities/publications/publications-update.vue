<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="universityAutomationApp.publications.home.createOrEditLabel" data-cy="PublicationsCreateUpdateHeading">
          Create or edit a Publications
        </h2>
        <div>
          <div class="form-group" v-if="publications.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="publications.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="publications-titulo">Titulo</label>
            <input
              type="text"
              class="form-control"
              name="titulo"
              id="publications-titulo"
              data-cy="titulo"
              :class="{ valid: !$v.publications.titulo.$invalid, invalid: $v.publications.titulo.$invalid }"
              v-model="$v.publications.titulo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="publications-preco">Preco</label>
            <input
              type="number"
              class="form-control"
              name="preco"
              id="publications-preco"
              data-cy="preco"
              :class="{ valid: !$v.publications.preco.$invalid, invalid: $v.publications.preco.$invalid }"
              v-model.number="$v.publications.preco.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="publications-dataPublicacao">Data Publicacao</label>
            <input
              type="text"
              class="form-control"
              name="dataPublicacao"
              id="publications-dataPublicacao"
              data-cy="dataPublicacao"
              :class="{ valid: !$v.publications.dataPublicacao.$invalid, invalid: $v.publications.dataPublicacao.$invalid }"
              v-model="$v.publications.dataPublicacao.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="publications-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.publications.status.$invalid, invalid: $v.publications.status.$invalid }"
              v-model="$v.publications.status.$model"
              id="publications-status"
              data-cy="status"
            >
              <option v-for="status in statusValues" :key="status" v-bind:value="status">{{ status }}</option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.publications.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./publications-update.component.ts"></script>
