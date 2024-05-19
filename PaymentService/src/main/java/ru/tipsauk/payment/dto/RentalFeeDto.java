package ru.tipsauk.payment.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RentalFeeDto {

    private long rentId;

    private float amount;

    private boolean paid;

    private String bankPaymentId;

}
