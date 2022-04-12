package com.turkcell.rentACar.entities.dtos.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerListDto {

    private int userId;
    private String email;
    private LocalDate registerDate;
}
