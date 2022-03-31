package com.turkcell.rentACar.entities.requests.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateColorRequest {

    @NotNull
    @Min(1)
    private int colorId;

    @NotBlank
    @NotNull
    @Size(min = 3, message = "Color name has to be greater than 3 chars.")
    private String colorName;
}
