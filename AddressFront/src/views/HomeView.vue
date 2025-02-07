<script setup>
import AddressesGrid from '@/components/Grids/AddressesGrid.vue'
import axiosClient from '@/client/axiosClient'
import { onMounted, ref, reactive } from 'vue'

const loading = ref(false)
const error = ref(false)
const cepSearch = ref()
const addresses = ref([])

const sortingState = reactive({
  sortField: null,
  sortDirection: null,
  sortOrders: { bairro: 1, localidade: 1, estado: 1 },
})

onMounted(() => {
  getAddresses()
})

function handleSearchByCep() {
  getAddresses()
}

function handleSort({ key }) {
  sortingState.sortOrders[key] *= -1
  sortingState.sortField = key
  sortingState.sortDirection = sortingState.sortOrders[key] === 1 ? 'asc' : 'desc'

  getAddresses()
}

async function getAddresses() {
  loading.value = true
  error.value = false

  try {
    const filters = {
      sortField: sortingState.sortField,
      sortDirection: sortingState.sortDirection,
      cep: cepSearch.value ? String(cepSearch.value) : undefined,
    }

    const response = await axiosClient.get('/cep', {
      params: filters,
      timeout: 5000,
    })

    if (response.data?.data?.content) {
      addresses.value = response.data.data.content
    } else {
      addresses.value = []
      error.value = true
    }
  } catch (err) {
    console.error('Erro ao carregar Registros de CEP:', err)
    error.value = true
    addresses.value = []
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <section class="bg-body-secondary">
    <h2 class="text-center py-5 fw-bold">BuscaCEP <i class="bi bi-house-up"></i></h2>
  </section>

  <section class="d-flex">
    <div class="row align-items-center justify-content-center gap-5">
      <div class="col-md-12 col-lg-12 mb-3">
        <div class="input-group">
          <span class="input-group-text" id="inputGroup2">
            <i class="bi bi-search"></i>
          </span>
          <input
            type="search"
            v-model="cepSearch"
            @input="handleSearchByCep"
            class="form-control"
            placeholder="CEP"
            aria-label="CEP"
            aria-describedby="inputGroup2"
          />
        </div>
      </div>
    </div>
  </section>

  <section>
    <AddressesGrid
      @sort="handleSort"
      v-if="!loading && !error"
      :data="addresses"
      :columns="[
        { key: 'id', name: 'ID' },
        { key: 'cep', name: 'CEP' },
        { key: 'logradouro', name: 'Logradouro' },
        { key: 'complemento', name: 'Complemento' },
        { key: 'unidade', name: 'Unidade' },
        { key: 'bairro', name: 'Bairro', type: 1 },
        { key: 'localidade', name: 'Localidade', type: 1 },
        { key: 'uf', name: 'UF' },
        { key: 'estado', name: 'Estado', type: 1 },
        { key: 'regiao', name: 'Regiao' },
        { key: 'ibge', name: 'IBGE' },
        { key: 'gia', name: 'GIA' },
        { key: 'ddd', name: 'DDD' },
        { key: 'siafi', name: 'SIAFI' },
      ]"
      :sort-key="sortingState.sortField"
      :sort-orders="sortingState.sortOrders"
    />
    <p v-else-if="loading">Carregando Registros...</p>
    <p v-else class="text-danger">Erro ao carregar Registros de CEP:</p>
  </section>
</template>
