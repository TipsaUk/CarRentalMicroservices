package ru.tipsauk.rental.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.tipsauk.rental.service.RentalService;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListenerTopics {

    private final RentalService rentalService;

    @KafkaListener(topics = "${spring.kafka.topic.paymentResponse}", groupId = "groupPayment")
    void listener(String data) {
        log.info("KafkaListenerTopics: Received message [{}] in paymentResponse", data);
        //rentalService.
    }

}
