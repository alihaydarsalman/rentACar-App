package com.turkcell.rentACar.api.models;

import com.turkcell.rentACar.entities.requests.create.CreatePaymentRequest;
import com.turkcell.rentACar.entities.requests.create.CreateRentalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {

    @Valid
    private CreateRentalRequest createRentalRequest;

    @Valid
    private CreatePaymentRequest createPaymentRequest;
}
