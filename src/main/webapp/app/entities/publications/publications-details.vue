<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="publications">
        <h2 class="jh-entity-heading" data-cy="publicationsDetailsHeading"><span>Publications</span> {{ publications.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Titulo</span>
          </dt>
          <dd>
            <span>{{ publications.titulo }}</span>
          </dd>
          <dt>
            <span>Preco</span>
          </dt>
          <dd>
            <span>{{ publications.preco }}</span>
          </dd>
          <dt>
            <span>Data Publicacao</span>
          </dt>
          <dd>
            <span>{{ publications.dataPublicacao }}</span>
          </dd>
          <dt>
            <span>Status</span>
          </dt>
          <dd>
            <span>{{ publications.status }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="publications.id"
          :to="{ name: 'PublicationsEdit', params: { publicationsId: publications.id } }"
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

<script lang="ts" src="./publications-details.component.ts"></script>
