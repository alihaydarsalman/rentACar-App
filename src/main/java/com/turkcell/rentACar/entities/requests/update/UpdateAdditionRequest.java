package com.turkcell.rentACar.entities.requests.update;


import com.turkcell.rentACar.business.constants.messages.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdditionRequest {

    @NotNull
    @Min(value = 1, message = ValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    int additionId;
    @NotNull
    @NotBlank
    private String additionName;
    @NotNull
    @Min(value = 0, message = ValidationMessages.PRICE_CANNOT_NEGATIVE)
    private double additionDailyPrice;
}
