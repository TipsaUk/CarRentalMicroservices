package ru.tipsauk.rental.controller.advice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.tipsauk.rental.exception.EntityCreateException;
import ru.tipsauk.rental.exception.EntityOperationException;
import ru.tipsauk.rental.exception.EntityUpdateException;

@ControllerAdvice
public class AdviceController {

    @Operation(
            summary = "Обработка ошибки создания аренды",
            description = "API для обработки ошибки создания аренды",
            responses = {
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании аренды")
            }
    )
    @ExceptionHandler(EntityCreateException.class)
    public ResponseEntity<String> handleException(EntityCreateException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @Operation(
            summary = "Обработка ошибки NotFound",
            description = "API для обработки ошибки NotFound",
            responses = {
                    @ApiResponse(responseCode = "404", description = "Ресурс не найден")
            }
    )
    @ExceptionHandler(EntityOperationException.class)
    public ResponseEntity<String> handleException(EntityOperationException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @Operation(
            summary = "Обработка ошибки обновления аренды",
            description = "API для обработки ошибки обновления аренды",
            responses = {
                    @ApiResponse(responseCode = "400", description = "Ошибка при обновления аренды")
            }
    )
    @ExceptionHandler(EntityUpdateException.class)
    public ResponseEntity<String> handleException(EntityUpdateException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

}
