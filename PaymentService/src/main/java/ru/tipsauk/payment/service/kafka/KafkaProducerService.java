package ru.tipsauk.payment.service.kafka;

import ru.tipsauk.payment.dto.RentalFeeDto;

public interface KafkaProducerService {

    void sendRentalFee(String topic, RentalFeeDto rentalFeeDto);

}
