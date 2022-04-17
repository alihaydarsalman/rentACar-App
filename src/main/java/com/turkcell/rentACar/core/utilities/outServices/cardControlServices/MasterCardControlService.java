package com.turkcell.rentACar.core.utilities.outServices.cardControlServices;

import org.springframework.stereotype.Service;

@Service
public class MasterCardControlService {

    public boolean verifyCard(String cardHolder,String cardNumber, int month, int year, int cvv){
        return true;
    }
}
