<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask">
      <div class="modal-container">
        <div class="modal-header d-flex justify-content-center">
          <h2>Formulário de Endereço</h2>
        </div>
        <form>
          <div class="d-flex justify-content-evenly mb-5 align-items-center cont-header">
            <div class="col-md-4">
              <label for="validationCustom01" class="form-label">CEP</label>
              <input
                type="text"
                class="form-control"
                v-model="cep"
                @input="applyCepMask"
                @blur="validateCep"
                maxlength="9"
                required
              />
              <p v-if="cepValidationMessage" class="text-danger">{{ cepValidationMessage }}</p>
            </div>
            <div>
              <div class="col-md-4 mt-4 w-100">
                <button class="btn btn-primary py-2 px-4" @click.prevent="handleCepSearch">
                  <span v-if="!loading">Pesquisa <i class="bi bi-search"></i></span>
                  <div v-else class="spinner-border text-light" role="status">
                    <span class="visually-hidden">Loading...</span>
                  </div>
                </button>
              </div>
            </div>
          </div>

          <div class="row g-2 justify-content-center">
            <div class="col-md-4">
              <label for="logradouro" class="form-label">Logradouro:</label>
              <input
                type="text"
                class="form-control"
                id="logradouro"
                v-model="logradouro"
                disabled
              />
            </div>

            <div class="col-md-4" id="cont-complemento">
              <label for="complemento" class="form-label">Complemento:</label>
              <input
                type="text"
                class="form-control"
                id="complemento"
                v-model="complemento"
                disabled
              />
            </div>

            <div class="col-md-2" id="cont-unidade">
              <label for="unidade" class="form-label">Unidade:</label>
              <input type="text" class="form-control" id="unidade" v-model="unidade" disabled />
            </div>

            <div class="col-md-3">
              <label for="bairro" class="form-label">Bairro:</label>
              <input type="text" class="form-control" id="bairro" v-model="bairro" disabled />
            </div>

            <div class="col-md-4">
              <label for="localidade" class="form-label">Localidade:</label>
              <input
                type="text"
                class="form-control"
                id="localidade"
                v-model="localidade"
                disabled
              />
            </div>

            <div class="col-md-1 col-sm-1 col-6">
              <label for="uf" class="form-label">UF:</label>
              <input type="text" class="form-control" id="uf" v-model="uf" disabled />
            </div>

            <div class="col-md-1 col-sm-1 col-6" id="cont-ddd">
              <label for="ddd" class="form-label">DDD:</label>
              <input type="text" class="form-control" id="ddd" v-model="ddd" disabled />
            </div>

            <div class="col-md-3">
              <label for="estado" class="form-label">Estado:</label>
              <input type="text" class="form-control" id="estado" v-model="estado" disabled />
            </div>

            <div class="col-md-2">
              <label for="regiao" class="form-label">Regiao:</label>
              <input type="text" class="form-control" id="regiao" v-model="regiao" disabled />
            </div>

            <div class="col-md-2" id="cont-ibge">
              <label for="ibge" class="form-label">IBGE:</label>
              <input type="text" class="form-control" id="ibge" v-model="ibge" disabled />
            </div>

            <div class="col-md-1" id="cont-gia">
              <label for="gia" class="form-label">GIA:</label>
              <input type="text" class="form-control" id="gia" v-model="gia" disabled />
            </div>

            <div class="col-md-1" id="cont-siafi">
              <label for="siafi" class="form-label">siafi:</label>
              <input type="text" class="form-control" id="siafi" v-model="siafi" disabled />
            </div>
          </div>
        </form>
        <div class="modal-footer d-flex justify-content-center mt-5">
          <button
            class="btn btn-success text-dark fw-bold px-3 py-2"
            @click.prevent="handleCepInsert"
          >
            Adicionar
          </button>
          <button class="btn btn-warning text-dark fw-bold px-3 py-2" @click.prevent="closeModal">
            Cancelar
          </button>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script setup>
import { ref } from 'vue'
import axiosClient from '@/client/axiosClient'

defineProps({
  show: {
    type: Boolean,
  },
})

const cep = ref()
const logradouro = ref()
const complemento = ref()
const unidade = ref()
const bairro = ref()
const localidade = ref()
const uf = ref()
const estado = ref()
const regiao = ref()
const ibge = ref()
const gia = ref()
const ddd = ref()
const siafi = ref()

const cepValidationMessage = ref('')
const loading = ref(false)

const cepBodyPayload = ref()

const emit = defineEmits(['cepInsert', 'close'])

function closeModal() {
  cleanFormInputs()
  emit('close')
}

function applyCepMask() {
  let rawCep = cep.value.replace(/\D/g, '')
  if (rawCep.length > 5) {
    cep.value = rawCep.slice(0, 5) + '-' + rawCep.slice(5, 8)
  } else {
    cep.value = rawCep
  }
}

function validateCep() {
  resetFormData()
  let rawCep = cep.value.replace(/\D/g, '')

  if (!rawCep) {
    cepValidationMessage.value = 'CEP deve ser informado antes de enviar'
    return false
  }

  if (rawCep.length !== 8) {
    cepValidationMessage.value = 'CEP deve conter exatamente 8 números'
    return false
  }

  cepValidationMessage.value = ''
  return true
}

function handleCepSearch() {
  if (!validateCep()) return

  getCepFromApi()
}

async function getCepFromApi() {
  loading.value = true
  try {
    const rawCep = cep.value.replace(/\D/g, '')

    const response = await axiosClient.get(`/cep/${rawCep}`, {
      timeout: 5000,
    })

    if (response.data?.data) {
      buildFoundCep(response.data.data)
    } else {
      resetFormData()
      cepValidationMessage.value = response.data.message
    }
  } catch (err) {
    console.error('Erro ao carregar Registros de CEP:', err)
    resetFormData()
    cepValidationMessage.value = err.response.data.message
  } finally {
    loading.value = false
  }
}

function buildFoundCep(data) {
  logradouro.value = data.logradouro
  complemento.value = data.complemento
  unidade.value = data.unidade
  bairro.value = data.bairro
  localidade.value = data.localidade
  uf.value = data.uf
  estado.value = data.estado
  regiao.value = data.regiao
  ibge.value = data.ibge
  gia.value = data.gia
  ddd.value = data.ddd
  siafi.value = data.siafi
}

function buildCepBodyPayload() {
  cepBodyPayload.value = {
    cep: cep.value.replace(/\D/g, ''),
    logradouro: logradouro.value,
    complemento: complemento.value,
    unidade: unidade.value,
    bairro: bairro.value,
    localidade: localidade.value,
    uf: uf.value,
    estado: estado.value,
    regiao: regiao.value,
    ibge: ibge.value,
    gia: gia.value,
    ddd: ddd.value,
    siafi: siafi.value,
  }
}

function resetFormData() {
  logradouro.value = ''
  complemento.value = ''
  unidade.value = ''
  bairro.value = ''
  localidade.value = ''
  uf.value = ''
  estado.value = ''
  regiao.value = ''
  ibge.value = ''
  gia.value = ''
  ddd.value = ''
  siafi.value = ''
}

function cleanFormInputs() {
  cep.value = ''
  resetFormData()
}

function handleCepInsert() {
  buildCepBodyPayload()
  emit('cepInsert', cepBodyPayload.value)
  cleanFormInputs()
  emit('close')
}
</script>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9994 !important; /* Essa parte pode ser ajustada */
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  transition: opacity 0.3s ease;
  backdrop-filter: blur(1px);
}

.modal-container {
  width: 50%;
  margin: auto;
  padding: 10px 20px;
  background-color: #adadad;
  border-radius: 0; /* Optional: Remove border radius for a clean edge */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  overflow-y: auto; /* Optional: Enable scrolling if content overflows */
}

.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  margin: 20px 0;
}

.modal-default-button {
  float: right;
}

.modal-enter-from {
  opacity: 0;
}

.modal-leave-to {
  opacity: 0;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
  -webkit-transform: translateX(20px); /* Slide-in animation from the right */
  transform: translateX(20px);
}

.modal-footer {
  width: 100%;
  gap: 2.5rem;
}

@media (max-width: 1200px) {
  .modal-container {
    width: 80%;
    height: 90%;
    overflow-y: auto; /* Optional: Enable scrolling if content overflows */
  }
  .modal-footer {
    gap: 1rem;
  }
}

@media (max-width: 600px) {
  .modal-container {
    width: 90%;
    height: 90%;
    overflow-y: auto; /* Optional: Enable scrolling if content overflows */
  }

  .cont-header {
    flex-direction: column;
  }
  #cont-gia {
    display: none;
  }
  #cont-siafi {
    display: none;
  }
  #cont-unidade {
    display: none;
  }
  #cont-ibge {
    display: none;
  }
  .modal-footer {
    width: 100%;
    gap: 0.5rem;
  }
}
</style>
