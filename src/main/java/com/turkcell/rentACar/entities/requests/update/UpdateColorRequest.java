package com.turkcell.rentACar.entities.requests.update;

import com.turkcell.rentACar.business.constants.messages.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateColorRequest {

    @NotNull
    @Min(value = 1, message = ValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    private int colorId;

    @NotBlank
    @NotNull
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ]{3,50}",
             message = ValidationMessages.COLOR_NAME_VALIDATION_ERROR)
    private String colorName;
}
