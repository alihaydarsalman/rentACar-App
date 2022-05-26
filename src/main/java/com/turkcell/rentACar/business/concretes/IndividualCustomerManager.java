package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.IndividualCustomerDao;
import com.turkcell.rentACar.entities.dtos.get.GetIndividualCustomerDto;
import com.turkcell.rentACar.entities.dtos.list.IndividualCustomerListDto;
import com.turkcell.rentACar.entities.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.entities.sourceEntities.IndividualCustomer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

    private final ModelMapperService modelMapperService;
    private final IndividualCustomerDao individualCustomerDao;
    private final UserService userService;

    public IndividualCustomerManager(ModelMapperService modelMapperService, IndividualCustomerDao individualCustomerDao
                                    , UserService userService) {
        this.modelMapperService = modelMapperService;
        this.individualCustomerDao = individualCustomerDao;
        this.userService=userService;
    }

    @Override
    public DataResult<List<IndividualCustomerListDto>> getAll() {

        List<IndividualCustomer> individualCustomers = this.individualCustomerDao.findAll();
        List<IndividualCustomerListDto> result = individualCustomers.stream()
                .map(individualCustomer -> this.modelMapperService.forDto().map(individualCustomer,IndividualCustomerListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<GetIndividualCustomerDto> getById(int userId) throws BusinessException {

        this.userService.isExistsUserByUserId(userId);
        isIndividualCustomerExistsById(userId);

        IndividualCustomer customer = this.individualCustomerDao.getById(userId);
        GetIndividualCustomerDto result = this.modelMapperService.forDto().map(customer,GetIndividualCustomerDto.class);

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_GET);
    }

    @Override
    public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {

        this.userService.isExistsUserByEmail(createIndividualCustomerRequest.getEmail());
        isIndividualCustomerExistsByNationalId(createIndividualCustomerRequest.getNationalId());

        IndividualCustomer customer= this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
        this.individualCustomerDao.save(customer);

        return new SuccessResult(BusinessMessages.SUCCESS_ADD);
    }

    @Override
    public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {

        this.userService.isExistsUserByUserId(updateIndividualCustomerRequest.getUserId());
        isIndividualCustomerExistsById(updateIndividualCustomerRequest.getUserId());
        this.userService.isExistsUserByEmail(updateIndividualCustomerRequest.getEmail());
        isIndividualCustomerExistsByNationalId(updateIndividualCustomerRequest.getNationalId());
        LocalDate creationDate = this.individualCustomerDao.findById(updateIndividualCustomerRequest.getUserId()).get().getRegisterDate();

        IndividualCustomer customer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);
        customer.setRegisterDate(creationDate);
        this.individualCustomerDao.save(customer);

        return new SuccessResult(BusinessMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result delete(int userId) throws BusinessException {

        this.userService.isExistsUserByUserId(userId);
        isIndividualCustomerExistsById(userId);

        this.individualCustomerDao.deleteById(userId);

        return new SuccessResult(BusinessMessages.SUCCESS_DELETE);
    }


    private void isIndividualCustomerExistsByNationalId(String nationalId) throws BusinessException {
        if(this.individualCustomerDao.existsIndividualCustomerByNationalId(nationalId)){
            throw new BusinessException(BusinessMessages.ERROR_NATIONAL_IDENTITY_ALREADY_EXISTS);
        }
    }

    @Override
    public IndividualCustomer getCustomerById(int id){
        return this.individualCustomerDao.getById(id);
    }

    @Override
    public void isIndividualCustomerExistsById(int id) throws BusinessException {
        if (!this.individualCustomerDao.existsById(id)){
            throw new BusinessException(BusinessMessages.ERROR_INDIVIDUAL_CUSTOMER_NOT_FOUND);
        }
    }
}
