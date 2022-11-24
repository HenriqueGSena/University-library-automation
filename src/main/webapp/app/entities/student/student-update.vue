<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="universityAutomationApp.student.home.createOrEditLabel" data-cy="StudentCreateUpdateHeading">Create or edit a Student</h2>
        <div>
          <div class="form-group" v-if="student.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="student.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="student-curso">Curso</label>
            <input
              type="text"
              class="form-control"
              name="curso"
              id="student-curso"
              data-cy="curso"
              :class="{ valid: !$v.student.curso.$invalid, invalid: $v.student.curso.$invalid }"
              v-model="$v.student.curso.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="student-registration">Registration</label>
            <select
              class="form-control"
              id="student-registration"
              data-cy="registration"
              name="registration"
              v-model="student.registration"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  student.registration && registrationOption.id === student.registration.id ? student.registration : registrationOption
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
            :disabled="$v.student.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./student-update.component.ts"></script>
