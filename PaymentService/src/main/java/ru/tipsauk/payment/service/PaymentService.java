package ru.tipsauk.payment.service;

import ru.tipsauk.payment.dto.RentalFeeDto;

public interface PaymentService {

    void createRentalPayment(RentalFeeDto rentalFeeDto);

    void completeRentalPayment(RentalFeeDto rentalFeeDto);

}
