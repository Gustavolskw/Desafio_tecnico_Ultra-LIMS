package com.service.addressAPI.Controller;

import com.service.addressAPI.Domain.Model.Cep;
import com.service.addressAPI.Domain.Request.CepRequestDTO;
import com.service.addressAPI.Domain.Response.ApiResponse;
import com.service.addressAPI.Domain.Response.CepResponseDTO;
import com.service.addressAPI.Domain.Response.PageableResponse;
import com.service.addressAPI.Service.CepService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/cep")
@RequiredArgsConstructor
public class CepController {

    private final CepService cepService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> inserirCep(@RequestBody CepRequestDTO cep){
       return ResponseEntity.ok().body(new ApiResponse("CEP inserido com sucesso a Base de Dados!", cepService.registerAddress(cep)));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> listarCep(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @RequestParam(name = "cep", required = false) String cep,
            @RequestParam(name = "sortField", required = false) String sortField,
            @RequestParam(name = "sortDirection", required = false) String sortDirection) {

        Page<Cep> pagedCep = cepService.getAddresses(pageable, cep, sortField, sortDirection);
        Page<CepResponseDTO> cepResponseDTO = pagedCep.map(CepResponseDTO::new);

        return ResponseEntity.ok().body(new ApiResponse("Lista de Registros de CEP", new PageableResponse<>(cepResponseDTO)));
    }

}
