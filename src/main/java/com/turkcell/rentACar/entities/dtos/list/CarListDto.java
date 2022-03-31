package com.turkcell.rentACar.entities.dtos.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarListDto {

    private String description;
    private int modelYear;
    private double dailyPrice;
    private String brandName;
    private String colorName;
}
