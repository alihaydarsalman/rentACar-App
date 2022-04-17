package com.turkcell.rentACar.business.paymentManagement.adapters.posAdapters.abstracts;

import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.requests.create.CreateCardInfoRequest;
import com.turkcell.rentACar.entities.requests.create.CreatePaymentRequest;

public interface PosService {
    Result makePayment(double paymentAmount, CreateCardInfoRequest createCardInfoRequest);
}
