package com.turkcell.rentACar.business.abstracts;


import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetCarDto;
import com.turkcell.rentACar.entities.dtos.list.CarListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCarRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateCarRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CarService {

    DataResult<List<CarListDto>> getAll();

    Result add(CreateCarRequest createCarRequest) throws BusinessException;

    Result update(UpdateCarRequest updateCarRequest) throws BusinessException;

    Result delete(int carId) throws BusinessException;

    DataResult<GetCarDto> getById(int carId) throws BusinessException;

    void isExistsByCarId(int carId) throws BusinessException;

    DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize);

    DataResult<List<CarListDto>> getByDailyPriceLessThanEqual(double dailyPrice);

    DataResult<List<CarListDto>> getByDailyPriceGreaterThanEqual(double dailyPrice);

    DataResult<List<CarListDto>> getSortedCarsByDailyPrice(Sort.Direction sortDirection);

    void isExistsBrandByBrandId(int brandId) throws BusinessException;

    void isExistsColorByColorId(int colorId) throws BusinessException;

    DataResult<List<CarListDto>> getAllByColorId(int colorId) throws BusinessException;

    DataResult<List<CarListDto>> getAllByBrandId(int brandId) throws BusinessException;

    DataResult<List<CarListDto>> getAllByModelYear(int modelYear);
}
