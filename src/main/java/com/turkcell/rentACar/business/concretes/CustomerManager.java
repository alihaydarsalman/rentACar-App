package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.dataAccess.CustomerDao;
import com.turkcell.rentACar.entities.dtos.get.GetCustomerDto;
import com.turkcell.rentACar.entities.dtos.list.CustomerListDto;
import com.turkcell.rentACar.entities.sourceEntities.Customer;
import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomerManager implements CustomerService {

    private final ModelMapperService modelMapperService;
    private final CustomerDao customerDao;


    public CustomerManager(ModelMapperService modelMapperService, CustomerDao customerDao) {
        this.modelMapperService = modelMapperService;
        this.customerDao = customerDao;
    }

    @Override
    public DataResult<List<CustomerListDto>> getAll() {

        List<Customer> customers = this.customerDao.findAll();
        List<CustomerListDto> result = customers.stream()
                .map(customer -> this.modelMapperService.forDto().map(customer,CustomerListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<GetCustomerDto> getById(int id) throws BusinessException {

        isCustomerExistsByCustomerId(id);

        Customer customer = this.customerDao.getById(id);
        GetCustomerDto result = this.modelMapperService.forDto().map(customer,GetCustomerDto.class);

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_GET);
    }

    @Override
    public Customer getCustomerByCustomerId(int userId) throws BusinessException {

        isCustomerExistsByCustomerId(userId);

        return this.customerDao.getById(userId);
    }

    @Override
    public void isCustomerExistsByCustomerId(int id) throws BusinessException {
        if (!this.customerDao.existsById(id)){
            throw new BusinessException(BusinessMessages.ERROR_CUSTOMER_NOT_FOUND);
        }
    }
}
