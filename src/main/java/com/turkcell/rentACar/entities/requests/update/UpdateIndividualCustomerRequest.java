package com.turkcell.rentACar.entities.requests.update;

import com.turkcell.rentACar.business.constants.messages.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {

    @NotNull
    @Min(value = 1, message = ValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    private int userId;

    @NotBlank
    @NotNull
    private String firstName;

    @NotBlank
    @NotNull
    private String lastName;

    @NotBlank
    @NotNull
    @Size(min = 11,max = 11)
    private String nationalId;

    @Email
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String password;

}
