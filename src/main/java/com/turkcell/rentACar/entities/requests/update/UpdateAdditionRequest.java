package com.turkcell.rentACar.entities.requests.update;


import com.turkcell.rentACar.business.constants.messages.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdditionRequest {

    @NotNull
    @Min(value = 1, message = ValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    int additionId;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ 0-9]{2,50}",
             message = ValidationMessages.ADDITION_NAME_VALIDATION_ERROR)
    private String additionName;

    @NotNull
    @Min(value = 0, message = ValidationMessages.PRICE_CANNOT_NEGATIVE)
    private double additionDailyPrice;
}
