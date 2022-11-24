<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="universityAutomationApp.periodicals.home.createOrEditLabel" data-cy="PeriodicalsCreateUpdateHeading">
          Create or edit a Periodicals
        </h2>
        <div>
          <div class="form-group" v-if="periodicals.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="periodicals.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="periodicals-volume">Volume</label>
            <input
              type="number"
              class="form-control"
              name="volume"
              id="periodicals-volume"
              data-cy="volume"
              :class="{ valid: !$v.periodicals.volume.$invalid, invalid: $v.periodicals.volume.$invalid }"
              v-model.number="$v.periodicals.volume.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="periodicals-numero">Numero</label>
            <input
              type="number"
              class="form-control"
              name="numero"
              id="periodicals-numero"
              data-cy="numero"
              :class="{ valid: !$v.periodicals.numero.$invalid, invalid: $v.periodicals.numero.$invalid }"
              v-model.number="$v.periodicals.numero.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="periodicals-issn">Issn</label>
            <input
              type="text"
              class="form-control"
              name="issn"
              id="periodicals-issn"
              data-cy="issn"
              :class="{ valid: !$v.periodicals.issn.$invalid, invalid: $v.periodicals.issn.$invalid }"
              v-model="$v.periodicals.issn.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="periodicals-publications">Publications</label>
            <select
              class="form-control"
              id="periodicals-publications"
              data-cy="publications"
              name="publications"
              v-model="periodicals.publications"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  periodicals.publications && publicationsOption.id === periodicals.publications.id
                    ? periodicals.publications
                    : publicationsOption
                "
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
            :disabled="$v.periodicals.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./periodicals-update.component.ts"></script>
