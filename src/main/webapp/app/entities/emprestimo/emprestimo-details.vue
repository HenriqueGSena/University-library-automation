<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="emprestimo">
        <h2 class="jh-entity-heading" data-cy="emprestimoDetailsHeading"><span>Emprestimo</span> {{ emprestimo.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Publications</span>
          </dt>
          <dd>
            <span v-for="(publications, i) in emprestimo.publications" :key="publications.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'PublicationsView', params: { publicationsId: publications.id } }">{{
                publications.id
              }}</router-link>
            </span>
          </dd>
          <dt>
            <span>Registration</span>
          </dt>
          <dd>
            <span v-for="(registration, i) in emprestimo.registrations" :key="registration.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'RegistrationView', params: { registrationId: registration.id } }">{{
                registration.id
              }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="emprestimo.id"
          :to="{ name: 'EmprestimoEdit', params: { emprestimoId: emprestimo.id } }"
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

<script lang="ts" src="./emprestimo-details.component.ts"></script>
