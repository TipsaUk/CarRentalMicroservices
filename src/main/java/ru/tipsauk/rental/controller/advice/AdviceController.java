package ru.tipsauk.rental.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.tipsauk.rental.exception.EntityCreateException;
import ru.tipsauk.rental.exception.EntityOperationException;
import ru.tipsauk.rental.exception.EntityUpdateException;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(EntityCreateException.class)
    public ResponseEntity<String> handleException(EntityCreateException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(EntityOperationException.class)
    public ResponseEntity<String> handleException(EntityOperationException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(EntityUpdateException.class)
    public ResponseEntity<String> handleException(EntityUpdateException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

}
