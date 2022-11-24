<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="librarian">
        <h2 class="jh-entity-heading" data-cy="librarianDetailsHeading"><span>Librarian</span> {{ librarian.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Email</span>
          </dt>
          <dd>
            <span>{{ librarian.email }}</span>
          </dd>
          <dt>
            <span>Registration</span>
          </dt>
          <dd>
            <div v-if="librarian.registration">
              <router-link :to="{ name: 'RegistrationView', params: { registrationId: librarian.registration.id } }">{{
                librarian.registration.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="librarian.id"
          :to="{ name: 'LibrarianEdit', params: { librarianId: librarian.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./librarian-details.component.ts"></script>
