package com.example.bankingmanagementsystemspringboot.Exception;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException exception){

        return  ResponseEntity.status(404).body(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception){

        return ResponseEntity.badRequest().body(exception.getMessage());
    }


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorized(UnauthorizedException exception){

        return ResponseEntity.status(401).body(exception.getMessage());
    }

    @ExceptionHandler(IdempotencyConflictException.class)
        public ResponseEntity<String> handleIdempotencyConflict(IdempotencyConflictException exception) {

            return ResponseEntity.status(409).body(exception.getMessage());
        }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException exception) {

        return ResponseEntity.status(409).body("A database integrity conflict occurred. The operation could not be completed.");
    }


    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<String> handleUnsupportedOperation(
            UnsupportedOperationException exception) {

        return ResponseEntity.status(501).body(exception.getMessage());
    }

    }


