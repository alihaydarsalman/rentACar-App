package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetRentalDto;
import com.turkcell.rentACar.entities.dtos.list.RentalListDto;
import com.turkcell.rentACar.entities.requests.create.CreateRentalRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateRentalRequest;

import java.util.List;

public interface RentalService {

    DataResult<List<RentalListDto>> getAll();

    Result addRentalForIndividualCustomer(CreateRentalRequest createRentalRequest) throws BusinessException;

    Result addRentalForCorporateCustomer(CreateRentalRequest createRentalRequest) throws BusinessException;

    Result update(UpdateRentalRequest updateRentalRequest) throws BusinessException;

    Result delete(int rentId) throws BusinessException;

    DataResult<GetRentalDto> getById(int rentId) throws BusinessException;

    DataResult<List<RentalListDto>> getByCarId(int carId) throws BusinessException;

    void isCarStillRented(int carId) throws BusinessException;
}
