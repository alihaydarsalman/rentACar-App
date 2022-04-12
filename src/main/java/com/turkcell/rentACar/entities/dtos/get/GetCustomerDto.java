package com.turkcell.rentACar.entities.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCustomerDto {

    private int userId;
    private String email;
    private LocalDate registerDate;
}
