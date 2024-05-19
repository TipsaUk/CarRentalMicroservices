package ru.tipsauk.rental.service.kafka.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.tipsauk.rental.dto.RentalFeeDto;
import ru.tipsauk.rental.service.RentalService;
import ru.tipsauk.rental.service.kafka.KafkaListenerTopics;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListenerTopicsImpl implements KafkaListenerTopics {

    private final RentalService rentalService;

    @Override
    @KafkaListener(topics = "${spring.kafka.topic.paymentResponse}"
            , groupId ="${spring.kafka.consumer.group-id}", containerFactory = "rentalFeeKafkaListenerContainerFactory")
    public void listener(RentalFeeDto rentalFeeDto) {
        log.info("KafkaListenerTopics: Received message [{}] in paymentResponse", rentalFeeDto);
        rentalService.confirmRentalPayment(rentalFeeDto);
    }

}
