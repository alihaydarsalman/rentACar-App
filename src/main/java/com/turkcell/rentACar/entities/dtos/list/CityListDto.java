package com.turkcell.rentACar.entities.dtos.list;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityListDto {

    private int cityId;
    private String cityName;
}
