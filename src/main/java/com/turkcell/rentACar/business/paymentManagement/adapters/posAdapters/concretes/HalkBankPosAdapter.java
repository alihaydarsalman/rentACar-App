package com.turkcell.rentACar.business.paymentManagement.adapters.posAdapters.concretes;

import com.turkcell.rentACar.business.paymentManagement.adapters.posAdapters.abstracts.PosService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.paymentManagement.outServices.HalkBankPosService;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.entities.requests.create.CreatePaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class HalkBankPosAdapter implements PosService {

    @Override
    public Result makePayment(double paymentAmount, CreatePaymentRequest createPaymentRequest) {

        HalkBankPosService halkBankPosService = new HalkBankPosService();

        boolean paymentResult = halkBankPosService.verifyPayment(createPaymentRequest.getCardHolder(),
                createPaymentRequest.getCardNumber(),
                createPaymentRequest.getMonth(),
                createPaymentRequest.getYear(),
                createPaymentRequest.getCvv(),
                paymentAmount);

        if (paymentResult){
            return new SuccessResult(BusinessMessages.PAYMENT_SUCCESS);
        }
        else
            return new ErrorResult(BusinessMessages.PAYMENT_FAIL);
    }
}
