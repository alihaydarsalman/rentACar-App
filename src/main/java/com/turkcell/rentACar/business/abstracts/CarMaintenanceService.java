package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetCarMaintenanceDto;
import com.turkcell.rentACar.entities.dtos.list.CarMaintenanceListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateCarMaintenanceRequest;

import java.time.LocalDate;
import java.util.List;

public interface CarMaintenanceService {

    DataResult<List<CarMaintenanceListDto>> getAll();

    Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;

    Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;

    Result delete(int maintenanceId) throws BusinessException;

    DataResult<GetCarMaintenanceDto> getById(int maintenanceId) throws BusinessException;

    DataResult<List<CarMaintenanceListDto>> getByCarId(int carId) throws BusinessException;

    void isExistsByMaintenanceId(int maintenanceId) throws BusinessException;

    void isExistsCarByCarId(int carId) throws BusinessException;

    void isCarUnderMaintenanceForRental(int carId, LocalDate rentDate) throws BusinessException;
}
