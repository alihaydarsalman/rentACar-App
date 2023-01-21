package com.turkcell.rentACar.entities.dtos.list;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class CustomerListDto {

    private int userId;
    private String email;
    private LocalDate registerDate;
}
