<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="periodicals">
        <h2 class="jh-entity-heading" data-cy="periodicalsDetailsHeading"><span>Periodicals</span> {{ periodicals.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Volume</span>
          </dt>
          <dd>
            <span>{{ periodicals.volume }}</span>
          </dd>
          <dt>
            <span>Numero</span>
          </dt>
          <dd>
            <span>{{ periodicals.numero }}</span>
          </dd>
          <dt>
            <span>Issn</span>
          </dt>
          <dd>
            <span>{{ periodicals.issn }}</span>
          </dd>
          <dt>
            <span>Publications</span>
          </dt>
          <dd>
            <div v-if="periodicals.publications">
              <router-link :to="{ name: 'PublicationsView', params: { publicationsId: periodicals.publications.id } }">{{
                periodicals.publications.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="periodicals.id"
          :to="{ name: 'PeriodicalsEdit', params: { periodicalsId: periodicals.id } }"
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

<script lang="ts" src="./periodicals-details.component.ts"></script>
