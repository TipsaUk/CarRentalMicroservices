package ru.tipsauk.payment.service.kafka;

import ru.tipsauk.payment.dto.RentalFeeDto;

public interface KafkaListenerTopics {

    void rentalFeeListener(RentalFeeDto rentalFeeDto);
}
