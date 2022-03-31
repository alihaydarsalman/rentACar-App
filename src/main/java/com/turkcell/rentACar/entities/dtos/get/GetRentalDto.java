package com.turkcell.rentACar.entities.dtos.get;

import com.turkcell.rentACar.entities.dtos.list.AdditionListDto;
import com.turkcell.rentACar.entities.sourceEntities.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRentalDto {

    private int rentId;
    private LocalDate rentDate;
    private LocalDate rentReturnDate;
    private int carId;
    private String fromCityName;
    private String  toCityName;
    private List<AdditionListDto> additionList;
}
