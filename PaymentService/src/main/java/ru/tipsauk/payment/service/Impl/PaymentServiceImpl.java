package ru.tipsauk.payment.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tipsauk.payment.dto.RentalFeeDto;
import ru.tipsauk.payment.service.PaymentService;
import ru.tipsauk.payment.service.kafka.KafkaProducerService;
import ru.tipsauk.payment.util.PaymentGenerator;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Value("${spring.kafka.topic.payment}")
    private String paymentTopic;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void createRentalPayment(RentalFeeDto rentalFeeDto) {
        log.info("Create rental payment: rent id: " + rentalFeeDto.getRentId()
                + " amount: " + rentalFeeDto.getAmount());
        // some logic for creating a payment...
        try {
            // pause to show the service in demo mode
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        completeRentalPayment(rentalFeeDto);
    }

    @Override
    public void completeRentalPayment(RentalFeeDto rentalFeeDto) {
        log.info("Process rental payment: rent id: " + rentalFeeDto.getRentId()
                + " amount: " + rentalFeeDto.getAmount());
        // some logic for processing payments from payment services...
        rentalFeeDto.setBankPaymentId(PaymentGenerator.generateUuid());
        rentalFeeDto.setPaid(true);
        kafkaProducerService.sendRentalFee(paymentTopic, rentalFeeDto);
    }

}
