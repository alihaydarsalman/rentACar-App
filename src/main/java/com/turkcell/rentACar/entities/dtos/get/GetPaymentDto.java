package com.turkcell.rentACar.entities.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPaymentDto {

    private int paymentId;
    private LocalDate paymentDate;
    private double paymentAmount;
    private int invoiceId;
}
