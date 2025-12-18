package com.dibimbing.api_manage_product.utils.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import com.dibimbing.api_manage_product.utils.exceptions.custom.*;
import com.dibimbing.api_manage_product.utils.exceptions.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import java.util.*;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO<List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }

        log.error("error while validation process, detail: ", ex.getBindingResult().getFieldErrors());

        ErrorResponseDTO<List<String>> error = new ErrorResponseDTO<>(
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            errors
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO<String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("Method argument type mismatch, detail: ", ex.getMessage());
        
        String errorDetail = String.format("Invalid value for id '%s'", ex.getValue());

        ErrorResponseDTO<String> error = new ErrorResponseDTO<>(
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            errorDetail
        );
        
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Malformed JSON request, detail: ", ex.getMessage());

        ErrorResponseDTO<String> error = new ErrorResponseDTO<>(
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            "JSON request not valid"
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO<String>> handleHttpRequestMethodNotSupportedException(Exception ex) {
        log.error("error while validation process, detail: ", ex.getMessage());

        ErrorResponseDTO<String> error = new ErrorResponseDTO<>(
            HttpStatus.METHOD_NOT_ALLOWED.value(),
            "Method Not Allowed",
            "Request method is not supported"
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDTO<String>> handleNoResourceFoundException(NoResourceFoundException ex) {
        log.error("error while validation process, detail: ", ex.getMessage());

        ErrorResponseDTO<String> error = new ErrorResponseDTO<>(
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            "No static resource found for request"
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDTO<String>> handleValidationException(ValidationException ex) {
        log.error("error while validation process, detail: ", ex.getMessage());
        
        ErrorResponseDTO<String> error = new ErrorResponseDTO<>(
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            ex.getMessage()
        );
        
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO<String>> handleNotFoundException(NotFoundException ex) {
        log.error("error while validation process, detail: ", ex.getMessage());
        
        ErrorResponseDTO<String> error = new ErrorResponseDTO<>(
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            ex.getMessage()
        );
        
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO<String>> handleBusinessException(BusinessException ex) {
        log.error("error while validation process, detail: ", ex.getMessage());
        
        ErrorResponseDTO<String> error = new ErrorResponseDTO<>(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "Something went wrong"
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
