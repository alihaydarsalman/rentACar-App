package com.turkcell.rentACar.business.paymentManagement.adapters.posAdapters.concretes;

import com.turkcell.rentACar.business.paymentManagement.adapters.posAdapters.abstracts.PosService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.paymentManagement.outServices.IsBankPosService;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.entities.requests.create.CreatePaymentRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class IsBankPosAdapter implements PosService {

    @Override
    public Result makePayment(double paymentAmount, CreatePaymentRequest createPaymentRequest) {

        IsBankPosService isBankPosService = new IsBankPosService();

        boolean paymentResult = isBankPosService.verifyPayment(createPaymentRequest.getCardNumber(),
                createPaymentRequest.getMonth(),
                createPaymentRequest.getYear(),
                createPaymentRequest.getCvv(),
                createPaymentRequest.getCardHolder(),
                paymentAmount);

        if (paymentResult){
            return new SuccessResult(BusinessMessages.PAYMENT_SUCCESS);
        }
        else
            return new ErrorResult(BusinessMessages.PAYMENT_FAIL);
    }
}