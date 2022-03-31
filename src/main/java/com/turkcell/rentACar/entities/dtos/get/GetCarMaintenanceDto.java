package com.turkcell.rentACar.entities.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarMaintenanceDto {

    private int maintenanceId;
    private String maintenanceDesc;
    private LocalDate maintenanceReturnDate;
    private String brandName;
    private String description;
}
