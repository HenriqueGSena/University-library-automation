<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="book">
        <h2 class="jh-entity-heading" data-cy="bookDetailsHeading"><span>Book</span> {{ book.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Autor</span>
          </dt>
          <dd>
            <span>{{ book.autor }}</span>
          </dd>
          <dt>
            <span>Isbn</span>
          </dt>
          <dd>
            <span>{{ book.isbn }}</span>
          </dd>
          <dt>
            <span>Editora</span>
          </dt>
          <dd>
            <span>{{ book.editora }}</span>
          </dd>
          <dt>
            <span>Edicao</span>
          </dt>
          <dd>
            <span>{{ book.edicao }}</span>
          </dd>
          <dt>
            <span>Publications</span>
          </dt>
          <dd>
            <div v-if="book.publications">
              <router-link :to="{ name: 'PublicationsView', params: { publicationsId: book.publications.id } }">{{
                book.publications.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="book.id" :to="{ name: 'BookEdit', params: { bookId: book.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./book-details.component.ts"></script>
