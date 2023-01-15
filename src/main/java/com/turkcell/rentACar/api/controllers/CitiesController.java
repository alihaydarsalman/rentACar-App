package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.dtos.get.GetCityDto;
import com.turkcell.rentACar.entities.dtos.list.CityListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCityRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateCityRequest;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {

    private final CityService cityService;

    public CitiesController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CityListDto>> getAll(){
        return this.cityService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<GetCityDto> getById(@RequestParam int cityId) throws BusinessException {
        return this.cityService.getById(cityId);
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCityRequest createCityRequest) throws BusinessException{
        return this.cityService.add(createCityRequest);
    }

    @PutMapping("/update")
    public DataResult<GetCityDto> update(@RequestBody @Valid UpdateCityRequest updateCityRequest) throws BusinessException{
        return this.cityService.update(updateCityRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int cityId) throws BusinessException{
        return this.cityService.delete(cityId);
    }
}
