package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.RentalService;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetRentalDto;
import com.turkcell.rentACar.entities.dtos.list.RentalListDto;
import com.turkcell.rentACar.entities.requests.create.CreateRentalRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateRentalRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

    private final RentalService rentalService;

    public RentalsController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/getAll")
    public DataResult<List<RentalListDto>> getAll()
    {
        return this.rentalService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateRentalRequest createRentalRequest) throws BusinessException
    {
        return this.rentalService.add(createRentalRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateRentalRequest updateRentalRequest) throws BusinessException
    {
        return this.rentalService.update(updateRentalRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int rentId) throws BusinessException
    {
        return this.rentalService.delete(rentId);
    }

    @GetMapping("/getById")
    public DataResult<GetRentalDto> getById(@RequestParam int rentId) throws BusinessException
    {
        return this.rentalService.getById(rentId);
    }

    @GetMapping("/getByCarId")
    public DataResult<List<RentalListDto>> getByCarId(@RequestParam int carId) throws BusinessException
    {
        return this.rentalService.getByCarId(carId);
    }
}