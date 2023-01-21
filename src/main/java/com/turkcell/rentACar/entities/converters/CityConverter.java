package com.turkcell.rentACar.entities.converters;

import com.turkcell.rentACar.entities.dtos.get.GetCityDto;
import com.turkcell.rentACar.entities.sourceEntities.City;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hzyazilimci
 */

@Component
public class CityConverter {

    public GetCityDto convertCityToDto(City city){
        return GetCityDto.builder()
                .cityId(city.getCityId())
                .cityName(city.getCityName())
                .build();
    }

    public List<GetCityDto> convertCityToDto(List<City> cities){
        return cities.stream().map(this::convertCityToDto).collect(Collectors.toList());
    }
}
