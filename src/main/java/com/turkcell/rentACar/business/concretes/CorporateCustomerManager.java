package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.CorporateCustomerDao;
import com.turkcell.rentACar.entities.dtos.get.GetCorporateCustomerDto;
import com.turkcell.rentACar.entities.dtos.list.CorporateCustomerListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.entities.sourceEntities.CorporateCustomer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

    private final ModelMapperService modelMapperService;
    private final CorporateCustomerDao corporateCustomerDao;
    private UserService userService;

    public CorporateCustomerManager(ModelMapperService modelMapperService, CorporateCustomerDao corporateCustomerDao
                                    ,UserService userService) {
        this.modelMapperService = modelMapperService;
        this.corporateCustomerDao = corporateCustomerDao;
        this.userService=userService;
    }

    @Override
    public DataResult<List<CorporateCustomerListDto>> getAll() {

        List<CorporateCustomer> corporateCustomers = this.corporateCustomerDao.findAll();
        List<CorporateCustomerListDto> result = corporateCustomers.stream()
                .map(corporateCustomer -> this.modelMapperService.forDto().map(corporateCustomer,CorporateCustomerListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<GetCorporateCustomerDto> getById(int userId) throws BusinessException {

        this.userService.isExistsUserByUserId(userId);

        CorporateCustomer customer = this.corporateCustomerDao.getById(userId);
        GetCorporateCustomerDto result = this.modelMapperService.forDto().map(customer,GetCorporateCustomerDto.class);

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_GET);
    }

    @Override
    public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {

        this.userService.isExistsUserByEmail(createCorporateCustomerRequest.getEmail());
        isCorporateCustomerExistByTaxNumber(createCorporateCustomerRequest.getTaxNumber());

        CorporateCustomer customer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest,CorporateCustomer.class);
        customer.setUserId(0);
        this.corporateCustomerDao.save(customer);

        return new SuccessResult(BusinessMessages.SUCCESS_ADD);
    }

    @Override
    public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {

        this.userService.isExistsUserByUserId(updateCorporateCustomerRequest.getUserId());
        this.userService.isExistsUserByEmail(updateCorporateCustomerRequest.getEmail());
        isCorporateCustomerExistByTaxNumber(updateCorporateCustomerRequest.getTaxNumber());

        CorporateCustomer customer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,CorporateCustomer.class);
        this.corporateCustomerDao.save(customer);

        return new SuccessResult(BusinessMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result delete(int userId) throws BusinessException {

        this.userService.isExistsUserByUserId(userId);

        this.corporateCustomerDao.deleteById(userId);

        return new SuccessResult(BusinessMessages.SUCCESS_DELETE);
    }


    private void isCorporateCustomerExistByTaxNumber(String taxNumber) throws BusinessException {
        if (this.corporateCustomerDao.existsCorporateCustomerByTaxNumber(taxNumber)){
            throw new BusinessException(BusinessMessages.ERROR_TAX_NUMBER_ALREADY_EXISTS);
        }
    }

}
