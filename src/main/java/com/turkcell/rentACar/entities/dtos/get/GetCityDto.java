package com.turkcell.rentACar.entities.dtos.get;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class GetCityDto {

    private int cityId;
    private String cityName;
}
