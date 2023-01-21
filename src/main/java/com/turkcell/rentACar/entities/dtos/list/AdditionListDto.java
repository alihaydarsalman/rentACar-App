package com.turkcell.rentACar.entities.dtos.list;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class AdditionListDto {

    int additionId;
    String additionName;
    double additionDailyPrice;
}
