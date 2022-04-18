package com.turkcell.rentACar.entities.requests.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentACar.business.constants.messages.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCardInfoRequest {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ ]{5,40}", message = ValidationMessages.PAYMENT_CARDHOLDER_VALIDATION_ERROR)
    private String cardHolder;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{16}", message = ValidationMessages.PAYMENT_CARD_NUMBER_VALIDATION_ERROR)
    private String cardNumber;

    @NotNull
    @Min(value = 100, message = ValidationMessages.PAYMENT_CVV_VALIDATION_ERROR)
    private int cvv;

    @NotNull(message = ValidationMessages.PAYMENT_MONTH_VALIDATION_ERROR)
    private int expirationMonth;

    @NotNull(message = ValidationMessages.PAYMENT_YEAR_VALIDATION_ERROR)
    private int expirationYear;

    @JsonIgnore
    private int userId;
}
