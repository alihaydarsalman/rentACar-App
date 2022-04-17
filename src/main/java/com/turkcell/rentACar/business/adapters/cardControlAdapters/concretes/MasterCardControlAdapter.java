package com.turkcell.rentACar.business.adapters.cardControlAdapters.concretes;

import com.turkcell.rentACar.business.adapters.cardControlAdapters.abstracts.CardControlService;
import com.turkcell.rentACar.core.utilities.outServices.cardControlServices.MasterCardControlService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.entities.requests.create.CreateCardInfoRequest;
import org.springframework.stereotype.Service;

@Service
public class MasterCardControlAdapter implements CardControlService {

    @Override
    public Result saveCard(CreateCardInfoRequest createCardInfoRequest) {

        MasterCardControlService masterCardControlService = new MasterCardControlService();

        boolean cardControlResult = masterCardControlService.verifyCard(createCardInfoRequest.getCardHolder(),
                createCardInfoRequest.getCardNumber(),
                createCardInfoRequest.getExpirationMonth(),
                createCardInfoRequest.getExpirationYear(),
                createCardInfoRequest.getCvv());

        if (cardControlResult){
            return new SuccessResult();
        }
        else
            return new ErrorResult(BusinessMessages.VERIFY_FAIL);
    }
}
