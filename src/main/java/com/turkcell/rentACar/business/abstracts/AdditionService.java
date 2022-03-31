package com.turkcell.rentACar.business.abstracts;



import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetAdditionDto;
import com.turkcell.rentACar.entities.dtos.list.AdditionListDto;
import com.turkcell.rentACar.entities.requests.create.CreateAdditionRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateAdditionRequest;
import com.turkcell.rentACar.entities.sourceEntities.Addition;

import java.util.List;

public interface AdditionService {


    DataResult<List<AdditionListDto>> getAll();

    DataResult<GetAdditionDto> getById(int additionId) throws BusinessException;

    Result add(CreateAdditionRequest createAdditionRequest) throws BusinessException;

    Result update(UpdateAdditionRequest updateAdditionRequest) throws BusinessException;

    Result delete(int additionId) throws BusinessException;

    void isExistsByAdditionId(int additionId) throws BusinessException;

    Addition getAdditionById(int additionId) throws BusinessException;

}
