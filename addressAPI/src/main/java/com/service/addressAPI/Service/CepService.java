package com.service.addressAPI.Service;

import com.service.addressAPI.Domain.Exception.InvalidCepException;
import com.service.addressAPI.Domain.Exception.SearchedListException;
import com.service.addressAPI.Domain.Model.Cep;
import com.service.addressAPI.Domain.Request.CepRequestDTO;
import com.service.addressAPI.Domain.Request.CepViaCepRequestDTO;
import com.service.addressAPI.Domain.Validation.CepValidation;
import com.service.addressAPI.Http.ViaCepClient;
import com.service.addressAPI.Repository.CepRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class CepService {
    private final CepRepository cepRepository;
    private final CepValidation cepValidation;
    private final ViaCepClient viaCepClient;

    public String registerAddress(CepRequestDTO cepRequestDTO){
        cepValidation.isAlreadySaved(cepRequestDTO.cep());
        Cep newCep = buildAddressToInsert(cepRequestDTO);
        cepRepository.save(newCep);
        return newCep.getCep();
    }

    public Page<Cep> getAddresses(Pageable pageable, String cep, String sortField, String sortDirection) {
        String searchedCep = (cep != null) ? cep + "%" : null;


        List<String> validFields = List.of("localidade", "bairro", "estado");
        if (sortField != null && !validFields.contains(sortField)) {
            sortField = null;
        }
        if (sortDirection != null && !(sortDirection.equalsIgnoreCase("asc") || sortDirection.equalsIgnoreCase("desc"))) {
            sortDirection = null;
        }


        Page<Cep> cepList = cepRepository.findByAllByParam(searchedCep, sortField, sortDirection, pageable);

        if (cepList.isEmpty()) {
            throw new SearchedListException("Lista Retornou vazia! Verifique os parâmetros de pesquisa.");
        }

        return cepList;
    }

    public Cep getAddress(String cepSearched) {
        cepValidation.isValidCep(cepSearched);
        Cep cep = cepRepository.findByCep(cepSearched).orElse(null);
        if (cep == null) {
            CepViaCepRequestDTO cepViaCepRequestDTO = requestViaCep(cepSearched);
            cep = buildAddressShow(cepViaCepRequestDTO);
            return cep;
        }
        return cep;
    }

    public Boolean isRegistered (Long cep){
        return cepRepository.findById(cep).isPresent();
    }


    private  CepViaCepRequestDTO requestViaCep(String cep){
        CepViaCepRequestDTO response = viaCepClient.buscaDadosDoCep(cep);
        if (response == null || response.getCep() == null || response.getCep().isEmpty()) {
            throw new InvalidCepException("Não foi possível encontrar um endereço para o CEP informado.");
        }
        response.setCep(response.getCep().replace("-", ""));
        return response;

    }

    private  Cep buildAddressShow(CepViaCepRequestDTO response) {
        Cep novoCep = new Cep();
        novoCep.setCep(response.getCep());
        novoCep.setLogradouro(response.getLogradouro());
        novoCep.setComplemento(response.getComplemento());
        novoCep.setUnidade(response.getUnidade());
        novoCep.setBairro(response.getBairro());
        novoCep.setLocalidade(response.getLocalidade());
        novoCep.setUf(response.getUf());
        novoCep.setEstado(response.getEstado());
        novoCep.setRegiao(response.getRegiao());
        novoCep.setIbge(response.getIbge());
        novoCep.setGia(response.getGia());
        novoCep.setDdd(response.getDdd());
        novoCep.setSiafi(response.getSiafi());
        return novoCep;
    }

    private  Cep buildAddressToInsert(CepRequestDTO response) {
        Cep novoCep = new Cep();
        novoCep.setCep(response.cep());
        novoCep.setLogradouro(response.logradouro());
        novoCep.setComplemento(response.complemento());
        novoCep.setUnidade(response.unidade());
        novoCep.setBairro(response.bairro());
        novoCep.setLocalidade(response.localidade());
        novoCep.setUf(response.uf());
        novoCep.setEstado(response.estado());
        novoCep.setRegiao(response.regiao());
        novoCep.setIbge(response.ibge());
        novoCep.setGia(response.gia());
        novoCep.setDdd(response.ddd());
        novoCep.setSiafi(response.siafi());
        return novoCep;
    }

}
