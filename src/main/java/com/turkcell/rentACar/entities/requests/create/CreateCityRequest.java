package com.turkcell.rentACar.entities.requests.create;

import com.turkcell.rentACar.business.constants.messages.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCityRequest {

    @NotNull
    @Min(value = 1, message = ValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    int plateNo;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ]{3,50}",
            message = ValidationMessages.CITY_NAME_VALIDATION_ERROR)
    private String cityName;
}
