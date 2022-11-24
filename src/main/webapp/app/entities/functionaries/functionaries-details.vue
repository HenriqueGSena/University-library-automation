<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="functionaries">
        <h2 class="jh-entity-heading" data-cy="functionariesDetailsHeading"><span>Functionaries</span> {{ functionaries.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Setor</span>
          </dt>
          <dd>
            <span>{{ functionaries.setor }}</span>
          </dd>
          <dt>
            <span>Registration</span>
          </dt>
          <dd>
            <div v-if="functionaries.registration">
              <router-link :to="{ name: 'RegistrationView', params: { registrationId: functionaries.registration.id } }">{{
                functionaries.registration.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="functionaries.id"
          :to="{ name: 'FunctionariesEdit', params: { functionariesId: functionaries.id } }"
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

<script lang="ts" src="./functionaries-details.component.ts"></script>
