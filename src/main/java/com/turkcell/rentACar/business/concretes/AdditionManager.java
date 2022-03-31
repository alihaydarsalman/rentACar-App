package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.AdditionService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.AdditionDao;
import com.turkcell.rentACar.entities.dtos.get.GetAdditionDto;
import com.turkcell.rentACar.entities.dtos.list.AdditionListDto;
import com.turkcell.rentACar.entities.requests.create.CreateAdditionRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateAdditionRequest;
import com.turkcell.rentACar.entities.sourceEntities.Addition;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdditionManager implements AdditionService {

    private final ModelMapperService modelMapperService;
    private final AdditionDao additionDao;

    public AdditionManager(ModelMapperService modelMapperService, AdditionDao additionDao) {
        this.modelMapperService = modelMapperService;
        this.additionDao = additionDao;
    }


    @Override
    public DataResult<List<AdditionListDto>> getAll() {

        List<Addition> additions = this.additionDao.findAll();
        List<AdditionListDto> result = additions.stream()
                .map(addition -> this.modelMapperService.forDto().map(addition,AdditionListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<GetAdditionDto> getById(int additionId) throws BusinessException {

        isExistsByAdditionId(additionId);

        Addition addition = this.additionDao.getById(additionId);
        GetAdditionDto result = this.modelMapperService.forDto().map(addition,GetAdditionDto.class);

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_GET);
    }

    @Override
    public Result add(CreateAdditionRequest createAdditionRequest) throws BusinessException {

        isExistsByAdditionName(createAdditionRequest.getAdditionName());

        Addition addition=this.modelMapperService.forRequest().map(createAdditionRequest,Addition.class);
        addition.setAdditionId(0);
        this.additionDao.save(addition);

        return new SuccessResult(BusinessMessages.SUCCESS_ADD);
    }

    @Override
    public Result update(UpdateAdditionRequest updateAdditionRequest) throws BusinessException {

        isExistsByAdditionId(updateAdditionRequest.getAdditionId());
        isExistsByAdditionName(updateAdditionRequest.getAdditionName());

        Addition addition = this.modelMapperService.forRequest().map(updateAdditionRequest,Addition.class);
        this.additionDao.save(addition);

        return new SuccessResult(BusinessMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result delete(int additionId) throws BusinessException {

        isExistsByAdditionId(additionId);

        this.additionDao.deleteById(additionId);

        return new SuccessResult(BusinessMessages.SUCCESS_DELETE);
    }

    @Override
    public Addition getAdditionById(int additionId) throws BusinessException {

        isExistsByAdditionId(additionId);

        return this.additionDao.findAdditionByAdditionId(additionId);
    }

    @Override
    public void isExistsByAdditionId(int additionId) throws BusinessException {
        if(!this.additionDao.existsByAdditionId(additionId)){
            throw new BusinessException(BusinessMessages.ERROR_ADDITION_NOT_FOUND);
        }
    }


    private void isExistsByAdditionName(String additionName) throws BusinessException {
        if(this.additionDao.existsByAdditionName(additionName)){
            throw new BusinessException(BusinessMessages.ERROR_ADDITION_NAME_EXISTS);
        }
    }

}
