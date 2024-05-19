package ru.tipsauk.rental.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.tipsauk.rental.entity.Car;
import ru.tipsauk.rental.entity.RentalStatus;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CarRentalDto {

    private long id;

    private ClientDto client;

    private CarDto car;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    private RentalStatus status;

    private boolean paid;

    private String bankPaymentId;

}
