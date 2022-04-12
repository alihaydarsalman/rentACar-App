package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetCarDamageDto;
import com.turkcell.rentACar.entities.dtos.list.CarDamageListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCarDamageRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateCarDamageRequest;

import java.util.List;

public interface CarDamageService {

    DataResult<List<CarDamageListDto>> getAll();

    DataResult<GetCarDamageDto> getById(int damageId) throws BusinessException;

    DataResult<List<CarDamageListDto>> getDamagesByCar(int carId) throws BusinessException;

    Result add(CreateCarDamageRequest createCarDamageRequest) throws BusinessException;

    Result update(UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException;

    Result delete(int damageId) throws BusinessException;

    //Result deleteByCarId(int carId) throws BusinessException;
}
