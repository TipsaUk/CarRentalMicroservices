package ru.tipsauk.rental.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RentalFeeDto {

    public RentalFeeDto(long rentId, float amount) {
        this.rentId = rentId;
        this.amount = amount;
    }

    private long rentId;
    private float amount;

    private boolean paid;

    private String bankPaymentId;

}
