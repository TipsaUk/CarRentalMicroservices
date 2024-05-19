package ru.tipsauk.payment.service.kafka.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.tipsauk.payment.dto.RentalFeeDto;
import ru.tipsauk.payment.service.PaymentService;
import ru.tipsauk.payment.service.kafka.KafkaListenerTopics;


@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListenerTopicsImpl implements KafkaListenerTopics {

    private final PaymentService paymentService;

    @Override
    @KafkaListener(topics = "${spring.kafka.topic.paymentResponse}"
            , groupId = "${spring.kafka.consumer.group-id}", containerFactory = "rentalFeeKafkaListenerContainerFactory")
    public void rentalFeeListener(RentalFeeDto rentalFeeDto) {
        log.info("KafkaListenerTopics: Received message [{}] in paymentResponse", rentalFeeDto);
        paymentService.createRentalPayment(rentalFeeDto);
    }

}
