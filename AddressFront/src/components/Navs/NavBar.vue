<template>
  <section class="d-flex justify-content-center gap-3">
    <div class="row align-items-center justify-content-center gap-5">
      <div class="col-md-12 col-lg-12 mb-3">
        <div class="input-group">
          <span class="input-group-text" id="inputGroup2">
            <i class="bi bi-search"></i>
          </span>
          <input
            type="search"
            v-model="cepSearch"
            @change.prevent="handleSearchByCep"
            class="form-control"
            placeholder="CEP"
            aria-label="CEP"
            aria-describedby="inputGroup2"
          />
        </div>
      </div>
    </div>
    <div>
      <button @click.prevent="handleSearchByCep" class="btn fs-4 text-primary mb-3">
        <i class="bi bi-compass"></i>
      </button>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue'
const cepSearch = ref()
const emits = defineEmits(['cepSearched'])

function handleSearchByCep() {
  emits('cepSearched', { cep: cepSearch.value })
}
</script>
