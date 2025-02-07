package com.service.addressAPI.Domain.Validation;

import com.service.addressAPI.Domain.Exception.AlreadyRegisteredCepException;
import com.service.addressAPI.Domain.Exception.InvalidCepException;
import com.service.addressAPI.Repository.CepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CepValidation {

    private final CepRepository cepRepository;

        public void isValidCep(Long cep) {
            if (cep == null) {
               throw new InvalidCepException("Valor do Cep Vazio!");
            }

            String cepStr = String.format("%08d", cep);

            if(!cepStr.matches("\\d{8}")){
                throw new InvalidCepException("Quantidade de caracteres inválidos!");
            }
        }


    public void isAlreadySaved(Long cep) {
        if(cepRepository.existsByCep(cep.toString())){
            throw new AlreadyRegisteredCepException("Cep enviado já esta registrado no banco de dados!");
        }
    }
}
