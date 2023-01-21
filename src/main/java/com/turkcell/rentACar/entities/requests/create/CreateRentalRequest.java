package com.turkcell.rentACar.entities.requests.create;

import com.turkcell.rentACar.business.constants.messages.ValidationMessages;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class CreateRentalRequest {

    @NotNull
    private LocalDate rentDate;

    @NotNull
    private LocalDate rentReturnDate;

    @NotNull
    @Min(value = 1, message = ValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    private int carId;

    @NotNull
    @Min(value = 1, message = ValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    private int fromCityId;

    @NotNull
    @Min(value = 1, message = ValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    private int toCityId;

    private List<Integer> additionId;

    @NotNull
    @Min(value = 1, message = ValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    private int userId;
}
