package com.turkcell.rentACar.core.utilities.outServices.posServices;

import org.springframework.stereotype.Service;

@Service
public class IsBankPosService {

    public boolean verifyPayment(String cardNumber, int month, int year, int cvv ,String cardHolder, double paymentAmount){
        return true;
    }
}
