package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.dtos.get.GetCarDto;
import com.turkcell.rentACar.entities.dtos.list.CarListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCarRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateCarRequest;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarsController {

    private final CarService carService;

    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CarListDto>> getAll() {
        return this.carService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCarRequest createCarRequest) throws BusinessException {
        return this.carService.add(createCarRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCarRequest updateCarRequest) throws BusinessException {
        return this.carService.update(updateCarRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int carId) throws BusinessException {
        return this.carService.delete(carId);
    }

    @GetMapping("/getById")
    public DataResult<GetCarDto> getById(@RequestParam int carId) throws BusinessException {
        return this.carService.getById(carId);
    }

    @GetMapping("/getAllPaged")
    public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize)
    {
        return this.carService.getAllPaged(pageNo,pageSize);
    }

    @GetMapping("/getByDailyPriceLessThanEqual")
    public DataResult<List<CarListDto>> getByDailyPriceLessThanEqual(@RequestParam double dailyPrice)
    {
        return this.carService.getByDailyPriceLessThanEqual(dailyPrice);
    }

    @GetMapping("/getByDailyPriceGreaterThanEqual")
    public DataResult<List<CarListDto>> getByDailyPriceGreaterThanEqual(@RequestParam double dailyPrice)
    {
        return this.carService.getByDailyPriceGreaterThanEqual(dailyPrice);
    }

    @GetMapping("getAllSortedByDailyPrice")
    public DataResult<List<CarListDto>> getSortedCarsByDailyPrice(Sort.Direction sortDirection)
    {
        return this.carService.getSortedCarsByDailyPrice(sortDirection);
    }

    @GetMapping("/getAllByColor")
    public DataResult<List<CarListDto>> getAllByColorId(@RequestParam int colorId) throws BusinessException {
        return this.carService.getAllByColorId(colorId);
    }

    @GetMapping("/getAllByBrand")
    public DataResult<List<CarListDto>> getAllByBrandId(@RequestParam int brandId) throws BusinessException {
        return this.carService.getAllByBrandId(brandId);
    }

    @GetMapping("/getAllByModelYear")
    public DataResult<List<CarListDto>> getAllByModelYear(@RequestParam int modelYear)
    {
        return this.carService.getAllByModelYear(modelYear);
    }
}