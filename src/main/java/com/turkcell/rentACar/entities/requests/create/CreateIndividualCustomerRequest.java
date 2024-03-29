package com.turkcell.rentACar.entities.requests.create;

import com.turkcell.rentACar.business.constants.messages.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest {

    @NotBlank
    @NotNull
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ ]{2,50}",
             message = ValidationMessages.CUSTOMER_NAME_VALIDATION_ERROR)
    private String firstName;

    @NotBlank
    @NotNull
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ ]{2,50}",
            message = ValidationMessages.CUSTOMER_NAME_VALIDATION_ERROR)
    private String lastName;

    @NotBlank
    @NotNull
    @Pattern(regexp = "^[0-9]{11}",
             message = ValidationMessages.NATIONAL_IDENTITY_VALIDATION_ERROR)
    private String nationalId;

    @Email
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>.]).{8,20}$",
             message = ValidationMessages.PASSWORD_VALIDATION_ERROR)
    private String password;
}
