<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="universityAutomationApp.teacher.home.createOrEditLabel" data-cy="TeacherCreateUpdateHeading">Create or edit a Teacher</h2>
        <div>
          <div class="form-group" v-if="teacher.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="teacher.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="teacher-materia">Materia</label>
            <input
              type="text"
              class="form-control"
              name="materia"
              id="teacher-materia"
              data-cy="materia"
              :class="{ valid: !$v.teacher.materia.$invalid, invalid: $v.teacher.materia.$invalid }"
              v-model="$v.teacher.materia.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="teacher-registration">Registration</label>
            <select
              class="form-control"
              id="teacher-registration"
              data-cy="registration"
              name="registration"
              v-model="teacher.registration"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  teacher.registration && registrationOption.id === teacher.registration.id ? teacher.registration : registrationOption
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
            :disabled="$v.teacher.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./teacher-update.component.ts"></script>
