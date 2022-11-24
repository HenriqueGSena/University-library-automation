<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="universityAutomationApp.librarian.home.createOrEditLabel" data-cy="LibrarianCreateUpdateHeading">
          Create or edit a Librarian
        </h2>
        <div>
          <div class="form-group" v-if="librarian.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="librarian.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="librarian-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="librarian-email"
              data-cy="email"
              :class="{ valid: !$v.librarian.email.$invalid, invalid: $v.librarian.email.$invalid }"
              v-model="$v.librarian.email.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="librarian-registration">Registration</label>
            <select
              class="form-control"
              id="librarian-registration"
              data-cy="registration"
              name="registration"
              v-model="librarian.registration"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  librarian.registration && registrationOption.id === librarian.registration.id
                    ? librarian.registration
                    : registrationOption
                "
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
            :disabled="$v.librarian.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./librarian-update.component.ts"></script>
