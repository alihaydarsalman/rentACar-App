package com.turkcell.rentACar.business.paymentManagement.outServices;

import org.springframework.stereotype.Service;

@Service
public class HalkBankPosService {

    public boolean verifyPayment(String cardHolder, String cardNumber, int month, int year, int cvv, double paymentAmount){
        return true;
    }
}
