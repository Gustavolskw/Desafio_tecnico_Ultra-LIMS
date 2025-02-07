<template>
  <Transition name="modal">
    <div v-if="alertShow" class="modal-mask">
      <div class="modal-container">
        <div class="d-flex justify-content-start">
          <button class="border-0 text-black bg-white" @click="closeModal">
            <i class="bi bi-x-lg fw-bolder fs-5"></i>
          </button>
        </div>
        <p
          :class="{
            'text-danger': props.status != 200,
            'text-success': props.status == 200,
          }"
          class="fw-bold"
        >
          {{ props.message }}
        </p>
        <!-- Barra de progpesso -->
        <div class="modal-footer">
          <progress :value="progressRate" max="1" class="progress-bar"></progress>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script setup>
import { ref, watch, onUnmounted, computed } from 'vue'

const props = defineProps({
  alertShow: {
    type: Boolean,
  },
  message: {
    type: String,
  },
  status: {
    type: Number,
  },
})

const emit = defineEmits(['close'])

const duration = 3500 // 5 segundos
const startTime = ref(null)
const elapsed = ref(0)
let animationFrame
let autoCloseTimeout

// Calcula a porcentagem do progresso da barra
const progressRate = computed(() => {
  return Math.min(elapsed.value / duration, 1)
})

// Atualiza o progresso da barra
const updateProgress = () => {
  if (!startTime.value) return
  elapsed.value = performance.now() - startTime.value

  if (elapsed.value >= duration) {
    closeModal()
  } else {
    animationFrame = requestAnimationFrame(updateProgress)
  }
}

// Fecha o modal manualmente ou automaticamente
const closeModal = () => {
  clearTimeout(autoCloseTimeout)
  cancelAnimationFrame(animationFrame)
  emit('close')
}

// Inicia o tempo de fechamento automático
watch(
  () => props.alertShow,
  (newValue) => {
    if (newValue) {
      startTime.value = performance.now()
      elapsed.value = 0
      updateProgress()
      autoCloseTimeout = setTimeout(() => {
        closeModal()
      }, duration)
    }
  },
)

// Limpa o timeout quando o componente for desmontado
onUnmounted(() => {
  clearTimeout(autoCloseTimeout)
  cancelAnimationFrame(animationFrame)
})
</script>

<style scoped>
/* Define o fundo invisível do modal */
.modal-mask {
  position: fixed;
  z-index: 9994 !important;
  top: 10%;
  right: 10px; /* Move um pouco para dentro da tela */
  transform: translateY(-50%);
  width: auto;
  height: auto;
  background: none;
  display: flex;
  justify-content: flex-end;
  transition:
    opacity 0.3s ease,
    transform 0.3s ease;
}

/* Estiliza o alerta compacto */
.modal-container {
  width: 320px;
  padding: 15px 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: -2px 2px 10px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

/* Ajustes para o texto da mensagem */
.modal-body {
  margin: 10px 0;
  font-size: 1rem;
}

/* Barra de progresso */
.progress-bar {
  width: 100%;
  height: 5px;
  appearance: none;
  border: none;
}

.progress-bar::-webkit-progress-bar {
  background-color: #eee;
  border-radius: 5px;
}

.progress-bar::-webkit-progress-value {
  background-color: #17a2b8;
  border-radius: 5px;
}

.progress-bar::-moz-progress-bar {
  background-color: #17a2b8;
  border-radius: 5px;
}

/* Animação ao fechar */
.fade-out {
  opacity: 0 !important;
  transform: translateX(20px);
  transition:
    opacity 0.3s ease,
    transform 0.3s ease;
}

/* Animação suave na entrada e saída */
.modal-enter-active {
  transition:
    opacity 0.3s ease,
    transform 0.3s ease;
}
.modal-leave-active {
  transition:
    opacity 0.3s ease,
    transform 0.3s ease;
}
.modal-enter-from {
  opacity: 0;
  transform: translateX(20px);
}
.modal-leave-to {
  opacity: 0;
  transform: translateX(20px);
}

/* Responsividade */
@media (max-width: 450px) {
  .modal-container {
    width: 250px;
  }
}
</style>
