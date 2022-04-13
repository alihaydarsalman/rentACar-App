package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.RentalService;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetRentalDto;
import com.turkcell.rentACar.entities.dtos.list.AdditionListDto;
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

    @PostMapping("/addForIndividual")
    public Result addForIndividualCustomer(@RequestBody @Valid CreateRentalRequest createRentalRequest) throws BusinessException {
        return this.rentalService.addRentalForIndividualCustomer(createRentalRequest);
    }

    @PostMapping("/addForCorporate")
    public Result addForCorporateCustomer(@RequestBody @Valid CreateRentalRequest createRentalRequest) throws BusinessException
    {
        return this.rentalService.addRentalForCorporateCustomer(createRentalRequest);
    }

    @PutMapping("/updateForIndividual")
    public Result updateForIndividualCustomer(@RequestBody @Valid UpdateRentalRequest updateRentalRequest) throws BusinessException
    {
        return this.rentalService.updateRentalForIndividualCustomer(updateRentalRequest);
    }

    @PutMapping("/updateForCorporate")
    public Result updateForCorporateCustomer(@RequestBody @Valid UpdateRentalRequest updateRentalRequest) throws BusinessException
    {
        return this.rentalService.updateRentalForCorporateCustomer(updateRentalRequest);
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

    @GetMapping("/getOrdersByRent")
    public DataResult<List<AdditionListDto>> getOrdersByRent(@RequestParam int rentId){
        return this.rentalService.getOrdersByRent(rentId);
    }

    @GetMapping("/getByCarId")
    public DataResult<List<RentalListDto>> getByCarId(@RequestParam int carId) throws BusinessException
    {
        return this.rentalService.getByCarId(carId);
    }

    //Asagidaki iki endpoint firma tarafindan kullanilacak

    @PutMapping("/deliver")
    public Result deliverCar(@RequestParam int rentId, @RequestParam int carId) throws BusinessException{
        return this.rentalService.deliverCar(rentId,carId);
    }

    @PutMapping("/receive")
    public Result receiveCar(@RequestParam int rentId,@RequestParam int carId, @RequestParam double returnKilometer) throws BusinessException{
        return this.rentalService.receiveCar(rentId,carId,returnKilometer);
    }
}
