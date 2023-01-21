package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetCityDto;
import com.turkcell.rentACar.entities.requests.create.CreateCityRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateCityRequest;

import java.util.List;

public interface CityService {

    void isExistsByCityId(int cityId) throws BusinessException;

    DataResult<List<GetCityDto>> getAll();
    DataResult<GetCityDto> getById(int cityId) throws BusinessException;
    Result add(CreateCityRequest createCityRequest) throws BusinessException;
    DataResult<GetCityDto> update(UpdateCityRequest updateCityRequest) throws BusinessException;
    Result delete(int cityId) throws BusinessException;
}
