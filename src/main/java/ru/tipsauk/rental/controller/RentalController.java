package ru.tipsauk.rental.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tipsauk.rental.dto.CarRentalDto;

import java.util.List;

@Tag(
        name = "API аренды автомобилей",
        description = "API для управления арендой автомобилей (создание, удаление, получение и др.)"
)
@RequestMapping("car-rental/rentals")
public interface RentalController {

    @Operation(
            summary = "Получение всех данных по арендам автомобилей",
            description = "API для получения всех данных по арендам автомобилей",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение",
                            content = {@Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CarRentalDto.class)))
                            })
            }
    )
    @GetMapping()
    ResponseEntity<List<CarRentalDto>> findAll();

    @Operation(
            summary = "Получение аренды по id",
            description = "API для получения аренды по id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CarRentalDto.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Аренда по id не найдена")
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<CarRentalDto> findById(@Parameter(description = "id аренды автомобиля") @PathVariable long id);

    @Operation(
            summary = "Создание новой аренды",
            description = "Регистрация новой аренды автомобиля"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Регистрация аренды успешна"),
            @ApiResponse(responseCode = "400", description = "Ошибка во время регистрации")
    })
    @PostMapping()
    ResponseEntity<String> create(@RequestBody CarRentalDto carRentalDto);

    @Operation(
            summary = "Обновление существующей аренды",
            description = "Обновление данных существующей аренды автомобиля"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Обновление аренды успешно"),
            @ApiResponse(responseCode = "400", description = "Ошибка во время обновления")
    })
    @PutMapping()
    ResponseEntity<String> update(@RequestBody CarRentalDto carRentalDto);

    @Operation(
            summary = "Удаление аренды по id",
            description = "Удаление данных существующей аренды автомобиля по id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удаление аренды успешно"),
            @ApiResponse(responseCode = "404", description = "Аренда по id не найдена")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@Parameter(description = "id аренды автомобиля") @PathVariable long id);


}
