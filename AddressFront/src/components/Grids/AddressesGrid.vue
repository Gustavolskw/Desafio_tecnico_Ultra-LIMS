<script setup>
import { defineProps, defineEmits } from 'vue'

const props = defineProps({
  data: Array,
  columns: Array,
  sortKey: String, // Received from parent
  sortOrders: Object, // Received from parent
})

const emits = defineEmits(['sort']) // Define event emitter

function sortBy(key) {
  if (!['id', 'bairro', 'localidade', 'estado'].includes(key)) {
    return
  }

  // Emit event to parent to handle sorting
  emits('sort', { key })
}

function capitalize(str) {
  return str.charAt(0).toUpperCase() + str.slice(1)
}
</script>

<template>
  <table v-if="props.data.length">
    <thead>
      <tr>
        <th @click.prevent="sortBy('id')" :class="{ active: sortKey === 'id' }">
          ID
          <span class="arrow" :class="{ asc: sortOrders.id > 0, dsc: sortOrders.id < 0 }"></span>
        </th>
        <th>CEP</th>
        <th>{{ capitalize('Logradouro') }}</th>
        <th>{{ capitalize('Complemento') }}</th>
        <th>{{ capitalize('Unidade') }}</th>

        <th @click.prevent="sortBy('bairro')" :class="{ active: sortKey === 'bairro' }">
          {{ capitalize('Bairro') }}
          <span
            class="arrow"
            :class="{ asc: sortOrders.bairro > 0, dsc: sortOrders.bairro < 0 }"
          ></span>
        </th>

        <th @click.prevent="sortBy('localidade')" :class="{ active: sortKey === 'localidade' }">
          {{ capitalize('Localidade') }}
          <span
            class="arrow"
            :class="{ asc: sortOrders.localidade > 0, dsc: sortOrders.localidade < 0 }"
          ></span>
        </th>

        <th>{{ capitalize('UF') }}</th>

        <th @click.prevent="sortBy('estado')" :class="{ active: sortKey === 'estado' }">
          {{ capitalize('Estado') }}
          <span
            class="arrow"
            :class="{ asc: sortOrders.estado > 0, dsc: sortOrders.estado < 0 }"
          ></span>
        </th>

        <th>{{ capitalize('Região') }}</th>
        <th>IBGE</th>
        <th>GIA</th>
        <th>DDD</th>
        <th>SIAFI</th>
      </tr>
    </thead>
    <tbody>
      <tr class="table-row-data" v-for="entry in props.data" :key="entry.id">
        <td v-for="col in props.columns" :key="col.key">
          <span> {{ entry[col.key] }}</span>
        </td>
      </tr>
    </tbody>
  </table>
  <p v-else>No matches found.</p>
</template>

<style>
table {
  border: 2px solid #42b983;
  border-radius: 3px;
  background-color: #fff;
}

th {
  background-color: #42b983;
  color: rgba(255, 255, 255, 0.66);
  cursor: pointer;
  user-select: none;
}

td {
  background-color: #f9f9f9;
}

th,
td {
  min-width: 100px;
  padding: 8px 15px;
}

.table-row-data {
  border-bottom: 1px solid lightgray;
}

th.active {
  color: #fff;
}

th.active .arrow {
  opacity: 1;
}

.arrow {
  display: inline-block;
  vertical-align: middle;
  width: 0;
  height: 0;
  margin-left: 2px;
  opacity: 0.66;
}

.arrow.asc {
  border-left: 4px solid transparent;
  border-right: 4px solid transparent;
  border-bottom: 4px solid #fff;
}

.arrow.dsc {
  border-left: 4px solid transparent;
  border-right: 4px solid transparent;
  border-top: 4px solid #fff;
}
</style>
