<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="universityAutomationApp.book.home.createOrEditLabel" data-cy="BookCreateUpdateHeading">Create or edit a Book</h2>
        <div>
          <div class="form-group" v-if="book.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="book.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="book-autor">Autor</label>
            <input
              type="text"
              class="form-control"
              name="autor"
              id="book-autor"
              data-cy="autor"
              :class="{ valid: !$v.book.autor.$invalid, invalid: $v.book.autor.$invalid }"
              v-model="$v.book.autor.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="book-isbn">Isbn</label>
            <input
              type="number"
              class="form-control"
              name="isbn"
              id="book-isbn"
              data-cy="isbn"
              :class="{ valid: !$v.book.isbn.$invalid, invalid: $v.book.isbn.$invalid }"
              v-model.number="$v.book.isbn.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="book-editora">Editora</label>
            <input
              type="text"
              class="form-control"
              name="editora"
              id="book-editora"
              data-cy="editora"
              :class="{ valid: !$v.book.editora.$invalid, invalid: $v.book.editora.$invalid }"
              v-model="$v.book.editora.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="book-edicao">Edicao</label>
            <input
              type="text"
              class="form-control"
              name="edicao"
              id="book-edicao"
              data-cy="edicao"
              :class="{ valid: !$v.book.edicao.$invalid, invalid: $v.book.edicao.$invalid }"
              v-model="$v.book.edicao.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="book-publications">Publications</label>
            <select class="form-control" id="book-publications" data-cy="publications" name="publications" v-model="book.publications">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="book.publications && publicationsOption.id === book.publications.id ? book.publications : publicationsOption"
                v-for="publicationsOption in publications"
                :key="publicationsOption.id"
              >
                {{ publicationsOption.id }}
              </option>
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
            :disabled="$v.book.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./book-update.component.ts"></script>
