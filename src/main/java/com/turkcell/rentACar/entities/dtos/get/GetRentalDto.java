package com.turkcell.rentACar.entities.dtos.get;

import com.turkcell.rentACar.entities.dtos.list.AdditionListDto;
import com.turkcell.rentACar.entities.dtos.list.CustomerListDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class GetRentalDto {

    private int rentId;
    private LocalDate rentDate;
    private LocalDate rentReturnDate;
    private String brandName;
    private String description;
    private double rentKilometer;
    private double rentReturnKilometer;
    private String fromCityName;
    private String  toCityName;
    private List<AdditionListDto> additionList;
    private CustomerListDto customerListDto;
}
