package ru.tipsauk.rental.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.tipsauk.rental.dto.CarDto;
import ru.tipsauk.rental.entity.Car;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarDto carToCarDto(Car carRental);

    Car carDtoToCar(CarDto carRentalDto);

}
