package com.turkcell.rentACar.entities.dtos.get;

import com.turkcell.rentACar.entities.dtos.list.RentalListDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetInvoiceDto {

    private int invoiceId;
    private String invoiceNo;
    private LocalDate creationDate;
    private LocalDate rentDate;
    private LocalDate rentReturnDate;
    private int totalRentalDays;
    private RentalListDto rentalListDto;
}
