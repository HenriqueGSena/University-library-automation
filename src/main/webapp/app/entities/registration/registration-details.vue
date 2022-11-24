<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="registration">
        <h2 class="jh-entity-heading" data-cy="registrationDetailsHeading"><span>Registration</span> {{ registration.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Matricula</span>
          </dt>
          <dd>
            <span>{{ registration.matricula }}</span>
          </dd>
          <dt>
            <span>Nome</span>
          </dt>
          <dd>
            <span>{{ registration.nome }}</span>
          </dd>
          <dt>
            <span>Data Nascimento</span>
          </dt>
          <dd>
            <span>{{ registration.dataNascimento }}</span>
          </dd>
          <dt>
            <span>Endereco</span>
          </dt>
          <dd>
            <span>{{ registration.endereco }}</span>
          </dd>
          <dt>
            <span>Telefone</span>
          </dt>
          <dd>
            <span>{{ registration.telefone }}</span>
          </dd>
          <dt>
            <span>Email</span>
          </dt>
          <dd>
            <span>{{ registration.email }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="registration.id"
          :to="{ name: 'RegistrationEdit', params: { registrationId: registration.id } }"
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

<script lang="ts" src="./registration-details.component.ts"></script>
