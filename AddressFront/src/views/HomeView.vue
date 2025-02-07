<script setup>
import AddressesGrid from '@/components/Grids/AddressesGrid.vue'
import AddCepModal from '@/components/Modals/AddCepModal.vue'
import AlertModal from '@/components/Alerts/AlertModal.vue'
import NavBar from '@/components/Navs/NavBar.vue'
import axiosClient from '@/client/axiosClient'
import { onMounted, ref, reactive } from 'vue'

const loading = ref(false)
const error = ref(false)
const cepSearch = ref()
const addresses = ref([])

const addCepModalShow = ref(false)

const alertModal = ref(false)
const alertMessageStatus = ref()
const alertMessage = ref()

const sortingState = reactive({
  sortField: null,
  sortDirection: null,
  sortOrders: { id: 1, bairro: 1, localidade: 1, estado: 1 },
})

onMounted(() => {
  getAddresses()
})

function handleSort({ key }) {
  if (key === 'id') {
    sortingState == null
    getAddresses()
  }
  sortingState.sortOrders[key] *= -1
  sortingState.sortField = key
  sortingState.sortDirection = sortingState.sortOrders[key] === 1 ? 'asc' : 'desc'

  getAddresses()
}

function handleCepSearch(filter) {
  console.log(filter.cep)
  if (filter !== null) {
    cepSearch.value = filter.cep
  }
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

async function insertAddressOnAPI(data) {
  loading.value = true
  error.value = false

  try {
    const response = await axiosClient.post('/cep', data, {
      timeout: 5000,
    })

    if (response.data?.data) {
      alertMessage.value = response.data.message
      alertMessageStatus.value = response.status
    } else {
      alertMessage.value = response.data.message
      alertMessageStatus.value = response.status
    }
  } catch (err) {
    console.error('Erro ao Inserir Registros de CEP:', err)
    error.value = true
    alertMessage.value = err.response.data.message
    alertMessageStatus.value = err.response.status
  } finally {
    alertModal.value = true
    getAddresses()
  }
}

function handleCepInsert(data) {
  insertAddressOnAPI(data)
}
</script>

<template>
  <section class="bg-body-secondary">
    <h2 class="text-center py-5 fw-bold">BuscaCEP <i class="bi bi-house-up"></i></h2>
  </section>

  <NavBar @cepSearched="handleCepSearch" @openAddCepModal="addCepModalShow = true"></NavBar>

  <section class="d-flex justify-content-center">
    <div v-if="!loading && !error" class="grid-container my-5">
      <AddressesGrid
        @sort="handleSort"
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
    </div>
    <p v-else-if="loading">Carregando Registros...</p>
    <p v-else class="text-danger">Erro ao carregar Registros de CEP:</p>
  </section>

  <Teleport to="body">
    <AddCepModal
      :show="addCepModalShow"
      @close="addCepModalShow = false"
      @cepInsert="handleCepInsert"
    >
    </AddCepModal>
  </Teleport>

  <Teleport to="body">
    <AlertModal
      @close="alertModal = false"
      :alertShow="alertModal"
      :message="alertMessage"
      :status="alertMessageStatus"
    >
    </AlertModal>
  </Teleport>
</template>

<style scoped>
.grid-container {
  width: 80%;
  overflow-x: auto;
}

@media (max-width: 1200px) {
  .grid-container {
    width: 90%;
  }
}
</style>
