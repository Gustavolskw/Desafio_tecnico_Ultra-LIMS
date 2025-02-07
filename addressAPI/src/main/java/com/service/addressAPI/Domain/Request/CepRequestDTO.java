package com.service.addressAPI.Domain.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CepRequestDTO(@NotBlank(message = "Cep deve ser informado!") String cep,
                            String logradouro,
                            String complemento,
                            String unidade,
                            String bairro,
                            @NotBlank(message = "Localidade deve ser informado!")String localidade,
                            @NotBlank(message = "UF deve ser informado!")String uf,
                            @NotBlank(message = "Estado deve ser informado!")String estado,
                            @NotBlank(message = "Região deve ser informado!")String regiao,
                            Integer ibge,
                            String gia,
                            Integer ddd,
                            String siafi) {
}
