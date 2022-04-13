package com.turkcell.rentACar.entities.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCarDto {

    private String brandName;
    private String description;
    private int modelYear;
    private String colorName;
    private double currentKilometer;
    private double dailyPrice;
}
