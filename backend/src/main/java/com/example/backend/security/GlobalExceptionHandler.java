package com.example.backend.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult()
                                .getAllErrors().stream()
                                .map(objectError -> objectError.getDefaultMessage())
                                .collect(Collectors.toList());

                        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
}
