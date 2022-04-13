package com.turkcell.rentACar.entities.requests.update;

import com.turkcell.rentACar.business.constants.messages.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {

    @NotNull
    @Min(value = 1, message = ValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    private int carId;

    @NotNull
    @Min(value = 0,message = ValidationMessages.PRICE_CANNOT_NEGATIVE)
    private double dailyPrice;

    @NotNull
    @Min(value = 1990, message = ValidationMessages.CAR_MODEL_YEAR_VALIDATION_ERROR)
    private int modelYear;

    @NotNull
    @Min(value = 0, message = ValidationMessages.KILOMETER_VALIDATION_ERROR)
    private double currentKilometer;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ 0-9]{3,100}",
             message = ValidationMessages.CAR_DESCRIPTION_VALIDATION_ERROR)
    private String description;

    @NotNull
    @Min(value = 1, message = ValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    private int brandId;

    @NotNull
    @Min(value = 1, message = ValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    private int colorId;
}
