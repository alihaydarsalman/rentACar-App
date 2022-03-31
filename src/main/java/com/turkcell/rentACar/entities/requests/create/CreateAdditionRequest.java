package com.turkcell.rentACar.entities.requests.create;


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
public class CreateAdditionRequest {

    @NotNull
    @NotBlank
    private String additionName;
    @NotNull
    @Min(value = 0, message = ValidationMessages.PRICE_CANNOT_NEGATIVE)
    private double additionDailyPrice;
    @NotNull
    int amountOfAddition;
}
