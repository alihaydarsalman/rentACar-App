package com.turkcell.rentACar.entities.converters;

import com.turkcell.rentACar.entities.dtos.list.AdditionListDto;
import com.turkcell.rentACar.entities.sourceEntities.Addition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hzyazilimci
 */

@Component
public class AdditionConverter {

    public AdditionListDto convertAdditionToDto(Addition addition){
        return AdditionListDto.builder()
                .additionId(addition.getAdditionId())
                .additionName(addition.getAdditionName())
                .additionDailyPrice(addition.getAdditionDailyPrice())
                .build();
    }

    public List<AdditionListDto> convertAdditionToDto(List<Addition> additions){
        return additions.stream().map(this::convertAdditionToDto).collect(Collectors.toList());
    }
}
