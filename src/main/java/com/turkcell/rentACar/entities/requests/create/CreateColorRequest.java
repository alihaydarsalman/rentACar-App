package com.turkcell.rentACar.entities.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateColorRequest {

    @NotNull
    @NotBlank
    @Size(min = 3, message = "Color name has to be greater than 3 chars.")
    private String colorName;
}
