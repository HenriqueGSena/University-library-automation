<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="universityAutomationApp.functionaries.home.createOrEditLabel" data-cy="FunctionariesCreateUpdateHeading">
          Create or edit a Functionaries
        </h2>
        <div>
          <div class="form-group" v-if="functionaries.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="functionaries.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="functionaries-setor">Setor</label>
            <input
              type="text"
              class="form-control"
              name="setor"
              id="functionaries-setor"
              data-cy="setor"
              :class="{ valid: !$v.functionaries.setor.$invalid, invalid: $v.functionaries.setor.$invalid }"
              v-model="$v.functionaries.setor.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="functionaries-registration">Registration</label>
            <select
              class="form-control"
              id="functionaries-registration"
              data-cy="registration"
              name="registration"
              v-model="functionaries.registration"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  functionaries.registration && registrationOption.id === functionaries.registration.id
                    ? functionaries.registration
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
            :disabled="$v.functionaries.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./functionaries-update.component.ts"></script>
