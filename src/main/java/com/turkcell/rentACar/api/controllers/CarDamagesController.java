package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.dtos.get.GetCarDamageDto;
import com.turkcell.rentACar.entities.dtos.list.CarDamageListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCarDamageRequest;
import com.turkcell.rentACar.business.abstracts.CarDamageService;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.requests.update.UpdateCarDamageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/damages")
public class CarDamagesController {

    private final CarDamageService carDamageService;

    public CarDamagesController(CarDamageService carDamageService) {
        this.carDamageService = carDamageService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CarDamageListDto>> getAll(){
        return this.carDamageService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<GetCarDamageDto> getById(@RequestParam int damageId) throws BusinessException {
        return this.carDamageService.getById(damageId);
    }

    @GetMapping("/getDamagesByCar")
    public DataResult<List<CarDamageListDto>> getDamagesByCar(@RequestParam int carId) throws BusinessException{
        return this.carDamageService.getDamagesByCar(carId);
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCarDamageRequest createCarDamageRequest) throws BusinessException{
        return this.carDamageService.add(createCarDamageRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException{
        return this.carDamageService.update(updateCarDamageRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int damageId) throws BusinessException{
        return this.carDamageService.delete(damageId);
    }

}
