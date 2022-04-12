package com.turkcell.rentACar.entities.dtos.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalListDto {

    private int rentId;
    private LocalDate rentDate;
    private LocalDate rentReturnDate;
    private String brandName;
    private String description;
    private String fromCityName;
    private String  toCityName;
    private List<AdditionListDto> additionList;
    private CustomerListDto customerListDto;
}
