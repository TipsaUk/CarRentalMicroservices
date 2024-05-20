package ru.tipsauk.rental.dto;

import lombok.*;
import ru.tipsauk.rental.entity.Transmission;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

    public CarDto(long id) {
        this.id = id;
    }

    private long id;

    private String brand;

    private String model;

    private String number;

    private Transmission transmission;

    private String color;

}
