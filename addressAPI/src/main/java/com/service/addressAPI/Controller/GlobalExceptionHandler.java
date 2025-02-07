package com.service.addressAPI.Controller;

import com.service.addressAPI.Domain.Exception.AlreadyRegisteredCepException;
import com.service.addressAPI.Domain.Exception.InvalidCepException;
import com.service.addressAPI.Domain.Exception.SearchedListException;
import com.service.addressAPI.Domain.Response.ApiResponse;
import feign.RetryableException;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponse> IOException(IOException e) {
        return ResponseEntity.status(422).body(new ApiResponse(e.getMessage(), e.getClass().getName())  );
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ApiResponse> ServletException(ServletException e) {
        return ResponseEntity.status(400).body(new ApiResponse(e.getMessage(), e.getClass().getName()) );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> Exception(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(400).body(new ApiResponse(e.getMessage(), e.getClass().getName()) );
    }

    @ExceptionHandler(InvalidCepException.class)
    public ResponseEntity<ApiResponse> InvalidCepException(InvalidCepException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(422).body(new ApiResponse(e.getMessage(),e.getClass().getName()) );
    }

    @ExceptionHandler(SearchedListException.class)
    public ResponseEntity<ApiResponse> SearchedListException(SearchedListException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(200).body(new ApiResponse(e.getMessage(), e.getClass().getName()) );
    }

    @ExceptionHandler(AlreadyRegisteredCepException.class)
    public ResponseEntity<ApiResponse> AlreadyRegisteredCepException(AlreadyRegisteredCepException e) {
        log.error(e.getMessage(), e, e.getClass().getName());
        return ResponseEntity.status(409).body(new ApiResponse(e.getMessage(), e.getClass().getName()) );
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<ApiResponse> AlreadyRegisteredCepException(RetryableException e) {
        log.error(e.getMessage(), e, e.getClass().getName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(
                        "Erro na comunicação com API ViaCep, tente novamente mais tarde!",
                        Map.of(
                                "Message", e.getMessage(),
                                "ErrorClass", e.getClass().getName()
                        )
                ));

    }
}
