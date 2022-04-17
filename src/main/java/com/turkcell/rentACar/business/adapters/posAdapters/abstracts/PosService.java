package com.turkcell.rentACar.business.adapters.posAdapters.abstracts;

import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.requests.create.CreateCardInfoRequest;

public interface PosService {
    Result makePayment(double paymentAmount, CreateCardInfoRequest createCardInfoRequest);
}
