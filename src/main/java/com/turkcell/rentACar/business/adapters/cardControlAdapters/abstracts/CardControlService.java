package com.turkcell.rentACar.business.adapters.cardControlAdapters.abstracts;

import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.requests.create.CreateCardInfoRequest;

public interface CardControlService {
    Result saveCard(CreateCardInfoRequest createCardInfoRequest);
}
