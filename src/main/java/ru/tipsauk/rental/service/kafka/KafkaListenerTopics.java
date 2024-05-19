package ru.tipsauk.rental.service.kafka;

import ru.tipsauk.rental.dto.RentalFeeDto;

public interface KafkaListenerTopics {

    void listener(RentalFeeDto data);
}
