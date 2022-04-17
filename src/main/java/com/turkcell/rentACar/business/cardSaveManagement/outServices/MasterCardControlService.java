package com.turkcell.rentACar.business.cardSaveManagement.outServices;

import org.springframework.stereotype.Service;

@Service
public class MasterCardControlService {

    public boolean verifyCard(String cardHolder,String cardNumber, int month, int year, int cvv){
        return true;
    }
}
