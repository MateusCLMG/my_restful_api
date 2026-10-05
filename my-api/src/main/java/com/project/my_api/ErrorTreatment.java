package com.project.my_api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice 
public class ErrorTreatment {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> treatValidationError(MethodArgumentNotValidException ex){
        List<ValidationError> cleanError = ex.getFieldErrors().stream()
            .map(erro -> new ValidationError(erro.getField(), erro.getDefaultMessage()))
            .toList();

        return ResponseEntity.badRequest().body(cleanError);
    }
}
