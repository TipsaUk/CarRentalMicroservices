package ru.tipsauk.payment.service.kafka.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.tipsauk.payment.dto.RentalFeeDto;
import ru.tipsauk.payment.service.kafka.KafkaProducerService;


@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, RentalFeeDto> rentalFeeKafkaTemplate;

    @Override
    public void sendRentalFee(String topic, RentalFeeDto rentalFeeDto) {
        log.info("KafkaProducerService: Sent message [{}] in [{}]", rentalFeeDto, topic);
        rentalFeeKafkaTemplate.send(topic, rentalFeeDto);
    }

}
