package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetCardInfoDto;
import com.turkcell.rentACar.entities.dtos.list.CardInfoListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCardInfoRequest;

import java.util.List;

public interface CardInfoService {

    Result add(CreateCardInfoRequest createCardInfoRequest) throws BusinessException;

    Result delete(int cardId) throws BusinessException;

    DataResult<List<CardInfoListDto>> getAll();

    DataResult<GetCardInfoDto> getById(int cardId) throws BusinessException;
}
