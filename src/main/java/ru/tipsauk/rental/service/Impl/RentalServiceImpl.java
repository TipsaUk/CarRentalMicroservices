package ru.tipsauk.rental.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tipsauk.rental.dto.CarRentalDto;
import ru.tipsauk.rental.entity.CarRental;
import ru.tipsauk.rental.exception.EntityOperationException;
import ru.tipsauk.rental.exception.EntityUpdateException;
import ru.tipsauk.rental.mapper.CarRentalMapper;
import ru.tipsauk.rental.repository.RentalRepository;
import ru.tipsauk.rental.service.RentalService;
import ru.tipsauk.rental.service.kafka.KafkaProducerService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    @Value("${spring.kafka.topic.payment}")
    private String paymentTopic;

    private final RentalRepository rentalRepository;

    private final KafkaProducerService kafkaProducerService;

    private final CarRentalMapper carRentalMapper;

    @Override
    public List<CarRentalDto> findAll() {
        log.info("RentalServiceImpl: findAll() (Start method)");
        return rentalRepository.findAll().stream()
                .map(carRentalMapper::carRentalToCarRentalDto)
                .collect(Collectors.toList());
    }

    @Override
    public CarRentalDto findById(long id) {
        log.info("RentalServiceImpl: findById(long id), id = " + id + " (Start method)");
        return carRentalMapper.carRentalToCarRentalDto(rentalRepository.findById(id)
                .orElseThrow(() -> new EntityOperationException("Car rental not found by id " + id)));
    }

    @Override
    public CarRental create(CarRentalDto carRentalDto) {
        log.info("RentalServiceImpl: create(CarRentalDto carRentalDto), " + carRentalDto + " (Start method)");
        return rentalRepository.save(carRentalMapper.carRentalDtoToCarRental(carRentalDto));
    }

    @Override
    public CarRental update(CarRentalDto carRentalDto) {
        log.info("RentalServiceImpl: update(CarRentalDto carRentalDto), " + carRentalDto + " (Start method)");
        if (carRentalDto.getId() == 0) {
            throw new EntityUpdateException("No id is specified for the car rental");
        }
        kafkaProducerService.send(paymentTopic, String.valueOf(carRentalDto.getId()));
        return rentalRepository.save(carRentalMapper.carRentalDtoToCarRental(carRentalDto));
    }

    @Override
    public void deleteById(long id) {
        log.info("RentalServiceImpl: delete(long id), id = " + id + " (Start method)");
        if (rentalRepository.findById(id).isEmpty()) {
            throw new EntityOperationException("Car rental not found by id " + id);
        }
        rentalRepository.deleteById(id);
    }
}
