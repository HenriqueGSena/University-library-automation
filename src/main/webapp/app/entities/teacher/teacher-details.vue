<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="teacher">
        <h2 class="jh-entity-heading" data-cy="teacherDetailsHeading"><span>Teacher</span> {{ teacher.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Materia</span>
          </dt>
          <dd>
            <span>{{ teacher.materia }}</span>
          </dd>
          <dt>
            <span>Registration</span>
          </dt>
          <dd>
            <div v-if="teacher.registration">
              <router-link :to="{ name: 'RegistrationView', params: { registrationId: teacher.registration.id } }">{{
                teacher.registration.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="teacher.id" :to="{ name: 'TeacherEdit', params: { teacherId: teacher.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./teacher-details.component.ts"></script>
