package ru.tipsauk.rental.controller.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.tipsauk.rental.controller.RentalController;
import ru.tipsauk.rental.dto.CarRentalDto;
import ru.tipsauk.rental.service.RentalService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RentalControllerImpl implements RentalController {

    private final RentalService rentalService;

    @Override
    public ResponseEntity<List<CarRentalDto>> findAll() {
        return ResponseEntity.ok(rentalService.findAll());
    }

    @Override
    public ResponseEntity<CarRentalDto> findById(long id) {
        return ResponseEntity.ok(rentalService.findById(id));
    }

    @Override
    public ResponseEntity<String> create(CarRentalDto carRentalDto) {
        rentalService.create(carRentalDto);
        return ResponseEntity.ok("The new car rental has been successfully added");
    }

    @Override
    public ResponseEntity<String> update(CarRentalDto carRentalDto) {
        rentalService.update(carRentalDto);
        return ResponseEntity.ok("The car rental has been successfully updated");
    }

    @Override
    public ResponseEntity<String> delete(long id) {
        rentalService.deleteById(id);
        return ResponseEntity.ok("The car rental has been successfully deleted");
    }
}
