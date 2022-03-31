package com.turkcell.rentACar.entities.requests.update;

import com.turkcell.rentACar.business.constants.messages.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBrandRequest {

    @NotNull
    @Min(value = 1, message = ValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    private int brandId;

    @NotNull
    @NotBlank
    @Size(min = 3, message = "Brand name has to be greater than 3 chars.")
    private String brandName;
}
