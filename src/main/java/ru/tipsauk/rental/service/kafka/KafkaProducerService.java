package ru.tipsauk.rental.service.kafka;

import ru.tipsauk.rental.dto.RentalFeeDto;

public interface KafkaProducerService {

    void sendRentalFee(String topic, RentalFeeDto rentalFeeDto);

}
