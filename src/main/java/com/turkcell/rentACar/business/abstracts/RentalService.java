package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetRentalDto;
import com.turkcell.rentACar.entities.dtos.list.AdditionListDto;
import com.turkcell.rentACar.entities.dtos.list.RentalListDto;
import com.turkcell.rentACar.entities.requests.create.CreateRentalRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateRentalRequest;
import com.turkcell.rentACar.entities.sourceEntities.Rental;

import java.util.List;

public interface RentalService {

    DataResult<List<RentalListDto>> getAll();

    Rental addRentalForIndividualCustomer(CreateRentalRequest createRentalRequest) throws BusinessException;

    Rental addRentalForCorporateCustomer(CreateRentalRequest createRentalRequest) throws BusinessException;

    Result updateRentalForIndividualCustomer(UpdateRentalRequest updateRentalRequest) throws BusinessException;

    Result updateRentalForCorporateCustomer(UpdateRentalRequest updateRentalRequest) throws BusinessException;

    Result deliverCar(int rentId, int carId) throws BusinessException; //Bu metodun kullanilacagi endpoint firma tarafindan kullanilacak

    Result receiveCar(int rentId, int carId, double returnKilometer) throws BusinessException; //Bu metodun kullanilacagi endpoint firma tarafindan kullanilacak

    Result delete(int rentId) throws BusinessException;

    DataResult<GetRentalDto> getById(int rentId) throws BusinessException;

    DataResult<List<AdditionListDto>> getOrdersByRent(int rentId);

    Rental getByRentalId(int rentId) throws BusinessException;

    DataResult<List<RentalListDto>> getByCarId(int carId) throws BusinessException;

    void isCarStillRented(int carId) throws BusinessException;

    void isRentalExistsByRentalId(int rentId) throws BusinessException;

    void setAdditionForRental(Rental rental, CreateRentalRequest createRentalRequest) throws BusinessException;
}
