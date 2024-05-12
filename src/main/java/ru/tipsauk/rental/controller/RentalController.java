package ru.tipsauk.rental.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tipsauk.rental.dto.CarRentalDto;

import java.util.List;

@RequestMapping("car-rental/rentals")
public interface RentalController {

    @GetMapping()
    ResponseEntity<List<CarRentalDto>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<CarRentalDto> findById(@PathVariable long id);

    @PostMapping()
    ResponseEntity<String> create(@RequestBody CarRentalDto carRentalDto);

    @PutMapping()
    ResponseEntity<String> update(@RequestBody CarRentalDto carRentalDto);

    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable long id);


}
