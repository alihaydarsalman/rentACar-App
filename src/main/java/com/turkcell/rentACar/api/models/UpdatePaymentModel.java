package com.turkcell.rentACar.api.models;

import com.turkcell.rentACar.entities.requests.create.CreateCardInfoRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentModel {

    @Valid
    private UpdateRentalRequestForDelay updateRentalRequestForDelay;

    @Valid
    private CreateCardInfoRequest createCardInfoRequest;

    @NotNull
    @Min(0)
    @Max(1)
    private int saveIt;
}
