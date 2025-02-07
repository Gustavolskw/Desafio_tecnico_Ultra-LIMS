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

        public void isValidCep(String cep) {
            if (cep.isEmpty()) {
               throw new InvalidCepException("Valor do Cep Vazio!");
            }

            if(!cep.matches("\\d{8}")){
                throw new InvalidCepException("Quantidade de caracteres inválidos!");
            }
        }


    public void isAlreadySaved(String cep) {
        if(cepRepository.existsByCep(cep)){
            throw new AlreadyRegisteredCepException("Cep enviado já esta registrado no banco de dados!");
        }
    }
}
