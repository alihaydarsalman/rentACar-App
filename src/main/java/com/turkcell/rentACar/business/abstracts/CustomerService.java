package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.dtos.get.GetCustomerDto;
import com.turkcell.rentACar.entities.dtos.list.CustomerListDto;
import com.turkcell.rentACar.entities.sourceEntities.Customer;

import java.util.List;

public interface CustomerService {

    Customer getCustomerByCustomerId(int userId) throws BusinessException; //for invoice manager

    DataResult<List<CustomerListDto>> getAll();

    DataResult<GetCustomerDto> getById(int id) throws BusinessException;

    void isCustomerExistsByCustomerId(int id) throws BusinessException;
}
