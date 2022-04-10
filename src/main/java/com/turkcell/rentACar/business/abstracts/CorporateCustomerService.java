package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetCorporateCustomerDto;
import com.turkcell.rentACar.entities.dtos.list.CorporateCustomerListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.entities.sourceEntities.CorporateCustomer;

import java.util.List;

public interface CorporateCustomerService {

    DataResult<List<CorporateCustomerListDto>> getAll();

    DataResult<GetCorporateCustomerDto> getById(int userId) throws BusinessException;

    Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;

    Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException;

    Result delete(int userId) throws BusinessException;

    CorporateCustomer getCustomerById(int id);

    void isCorporateCustomerExistsById(int id) throws BusinessException;
}
