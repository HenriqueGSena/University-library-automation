<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="universityAutomationApp.registration.home.createOrEditLabel" data-cy="RegistrationCreateUpdateHeading">
          Create or edit a Registration
        </h2>
        <div>
          <div class="form-group" v-if="registration.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="registration.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="registration-matricula">Matricula</label>
            <input
              type="number"
              class="form-control"
              name="matricula"
              id="registration-matricula"
              data-cy="matricula"
              :class="{ valid: !$v.registration.matricula.$invalid, invalid: $v.registration.matricula.$invalid }"
              v-model.number="$v.registration.matricula.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="registration-nome">Nome</label>
            <input
              type="text"
              class="form-control"
              name="nome"
              id="registration-nome"
              data-cy="nome"
              :class="{ valid: !$v.registration.nome.$invalid, invalid: $v.registration.nome.$invalid }"
              v-model="$v.registration.nome.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="registration-dataNascimento">Data Nascimento</label>
            <input
              type="text"
              class="form-control"
              name="dataNascimento"
              id="registration-dataNascimento"
              data-cy="dataNascimento"
              :class="{ valid: !$v.registration.dataNascimento.$invalid, invalid: $v.registration.dataNascimento.$invalid }"
              v-model="$v.registration.dataNascimento.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="registration-endereco">Endereco</label>
            <input
              type="text"
              class="form-control"
              name="endereco"
              id="registration-endereco"
              data-cy="endereco"
              :class="{ valid: !$v.registration.endereco.$invalid, invalid: $v.registration.endereco.$invalid }"
              v-model="$v.registration.endereco.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="registration-telefone">Telefone</label>
            <input
              type="text"
              class="form-control"
              name="telefone"
              id="registration-telefone"
              data-cy="telefone"
              :class="{ valid: !$v.registration.telefone.$invalid, invalid: $v.registration.telefone.$invalid }"
              v-model="$v.registration.telefone.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="registration-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="registration-email"
              data-cy="email"
              :class="{ valid: !$v.registration.email.$invalid, invalid: $v.registration.email.$invalid }"
              v-model="$v.registration.email.$model"
            />
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
            :disabled="$v.registration.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./registration-update.component.ts"></script>
