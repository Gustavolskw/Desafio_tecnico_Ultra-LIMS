package com.service.addressAPI.Http;

import com.service.addressAPI.Domain.Request.CepViaCepRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ViaCepApi", url = "https://viacep.com.br/ws")
public interface ViaCepClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{cep}/json")
    CepViaCepRequestDTO buscaDadosDoCep(@PathVariable Long cep);
}
