package com.service.addressAPI.Domain.Response;

import com.service.addressAPI.Domain.Model.Cep;

public record CepSearchedResponseDTO(String cep,
                                     String logradouro,
                                     String complemento,
                                     String unidade,
                                     String bairro,
                                     String localidade,
                                     String uf,
                                     String estado,
                                     String regiao,
                                     Integer ibge,
                                     String gia,
                                     Integer ddd,
                                     String siafi) {

    public CepSearchedResponseDTO(Cep c){
        this(
                c.getCep(),
                c.getLogradouro(),
                c.getComplemento(),
                c.getUnidade(),
                c.getBairro(),
                c.getLocalidade(),
                c.getUf(),
                c.getEstado(),
                c.getRegiao(),
                c.getIbge(),
                c.getGia(),
                c.getDdd(),
                c.getSiafi()
        );
    }
}
