package com.turkcell.rentACar.entities.dtos.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateCustomerListDto {

    private int userId;
    private String companyName;
    private String taxNumber;
    private String email;
}
