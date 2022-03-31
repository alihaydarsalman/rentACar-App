package com.turkcell.rentACar.entities.dtos.list;


import com.turkcell.rentACar.entities.dtos.get.GetAdditionDto;
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
    private int carId;
    private String fromCityName;
    private String  toCityName;
    private List<GetAdditionDto> additionList;
}
