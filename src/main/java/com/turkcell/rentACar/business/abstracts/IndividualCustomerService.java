package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetIndividualCustomerDto;
import com.turkcell.rentACar.entities.dtos.list.IndividualCustomerListDto;
import com.turkcell.rentACar.entities.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.entities.sourceEntities.IndividualCustomer;

import java.util.List;

public interface IndividualCustomerService {

    DataResult<List<IndividualCustomerListDto>> getAll();

    DataResult<GetIndividualCustomerDto> getById(int userId) throws BusinessException;

    Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException;

    Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException;

    Result delete(int userId) throws BusinessException;

    IndividualCustomer getCustomerById(int id);

    void isIndividualCustomerExistsById(int id) throws BusinessException;

}
