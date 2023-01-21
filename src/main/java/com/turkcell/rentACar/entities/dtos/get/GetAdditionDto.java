package com.turkcell.rentACar.entities.dtos.get;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAdditionDto {

    int additionId;
    String additionName;
    double additionDailyPrice;
}
