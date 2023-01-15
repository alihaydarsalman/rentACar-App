package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.dtos.get.GetIndividualCustomerDto;
import com.turkcell.rentACar.entities.dtos.list.IndividualCustomerListDto;
import com.turkcell.rentACar.entities.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomersController {

    private final IndividualCustomerService individualCustomerService;

    public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
        this.individualCustomerService = individualCustomerService;
    }

    @GetMapping("/getAll")
    public DataResult<List<IndividualCustomerListDto>> getAll(){
        return this.individualCustomerService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<GetIndividualCustomerDto> getById(@RequestParam int userId) throws BusinessException {
        return this.individualCustomerService.getById(userId);
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException{
        return this.individualCustomerService.add(createIndividualCustomerRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException{
        return this.individualCustomerService.update(updateIndividualCustomerRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int userId) throws BusinessException{
        return this.individualCustomerService.delete(userId);
    }
}
