package ru.tipsauk.rental.service;

import ru.tipsauk.rental.dto.CarRentalDto;
import ru.tipsauk.rental.dto.RentalFeeDto;
import ru.tipsauk.rental.entity.CarRental;


public interface RentalService extends EntityService<CarRentalDto, CarRental> {

    void confirmRentalPayment(RentalFeeDto rentalFeeDto);

}
