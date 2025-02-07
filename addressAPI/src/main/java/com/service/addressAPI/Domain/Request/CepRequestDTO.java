package com.service.addressAPI.Domain.Request;

import jakarta.validation.constraints.NotNull;

public record CepRequestDTO(@NotNull(message = "Valor do Cep deve ser inserido!") Long cep) {
}
