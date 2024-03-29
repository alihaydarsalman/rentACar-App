package com.turkcell.rentACar.business.adapters.posAdapters.concretes;

import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.outServices.posServices.HalkBankPosService;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.entities.requests.create.CreateCardInfoRequest;
import com.turkcell.rentACar.business.adapters.posAdapters.abstracts.PosService;
import org.springframework.stereotype.Service;

@Service
public class HalkBankPosAdapter implements PosService {

    @Override
    public Result makePayment(double paymentAmount, CreateCardInfoRequest createCardInfoRequest) {

        HalkBankPosService halkBankPosService = new HalkBankPosService();

        boolean paymentResult = halkBankPosService.verifyPayment(createCardInfoRequest.getCardHolder(),
                createCardInfoRequest.getCardNumber(),
                createCardInfoRequest.getExpirationMonth(),
                createCardInfoRequest.getExpirationYear(),
                createCardInfoRequest.getCvv(),
                paymentAmount);

        if (paymentResult){
            return new SuccessResult(BusinessMessages.PAYMENT_SUCCESS);
        }
        else
            return new ErrorResult(BusinessMessages.PAYMENT_FAIL);
    }
}
