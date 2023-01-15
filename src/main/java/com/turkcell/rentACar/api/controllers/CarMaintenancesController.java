package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.dtos.get.GetCarMaintenanceDto;
import com.turkcell.rentACar.entities.dtos.list.CarMaintenanceListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
public class CarMaintenancesController {

    private final CarMaintenanceService carMaintenanceService;

    public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
        this.carMaintenanceService = carMaintenanceService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CarMaintenanceListDto>> getAll()
    {
        return this.carMaintenanceService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException
    {
        return this.carMaintenanceService.add(createCarMaintenanceRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException
    {
        return this.carMaintenanceService.update(updateCarMaintenanceRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int maintenanceId) throws BusinessException
    {
        return this.carMaintenanceService.delete(maintenanceId);
    }

    @GetMapping("/getByCarId")
    public DataResult<List<CarMaintenanceListDto>> getByCarId(@RequestParam int carId) throws BusinessException
    {
        return this.carMaintenanceService.getByCarId(carId);
    }

    @GetMapping("/getById")
    public DataResult<GetCarMaintenanceDto> getById(@RequestParam int maintenanceId) throws BusinessException {
        return this.carMaintenanceService.getById(maintenanceId);
    }
}
