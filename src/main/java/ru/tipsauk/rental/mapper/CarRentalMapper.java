package ru.tipsauk.rental.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.tipsauk.rental.dto.CarRentalDto;
import ru.tipsauk.rental.entity.CarRental;

@Mapper(componentModel = "spring", uses = {ClientMapper.class, CarMapper.class})
public interface CarRentalMapper {

    CarRentalDto carRentalToCarRentalDto(CarRental carRental);

    CarRental carRentalDtoToCarRental(CarRentalDto carRentalDto);

}
