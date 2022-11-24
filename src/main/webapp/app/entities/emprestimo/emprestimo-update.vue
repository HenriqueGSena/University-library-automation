<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="universityAutomationApp.emprestimo.home.createOrEditLabel" data-cy="EmprestimoCreateUpdateHeading">
          Create or edit a Emprestimo
        </h2>
        <div>
          <div class="form-group" v-if="emprestimo.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="emprestimo.id" readonly />
          </div>
          <div class="form-group">
            <label for="emprestimo-publications">Publications</label>
            <select
              class="form-control"
              id="emprestimo-publications"
              data-cy="publications"
              multiple
              name="publications"
              v-if="emprestimo.publications !== undefined"
              v-model="emprestimo.publications"
            >
              <option
                v-bind:value="getSelected(emprestimo.publications, publicationsOption)"
                v-for="publicationsOption in publications"
                :key="publicationsOption.id"
              >
                {{ publicationsOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label for="emprestimo-registration">Registration</label>
            <select
              class="form-control"
              id="emprestimo-registrations"
              data-cy="registration"
              multiple
              name="registration"
              v-if="emprestimo.registrations !== undefined"
              v-model="emprestimo.registrations"
            >
              <option
                v-bind:value="getSelected(emprestimo.registrations, registrationOption)"
                v-for="registrationOption in registrations"
                :key="registrationOption.id"
              >
                {{ registrationOption.id }}
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
            :disabled="$v.emprestimo.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./emprestimo-update.component.ts"></script>
