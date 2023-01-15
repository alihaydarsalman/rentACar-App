package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.dtos.get.GetCorporateCustomerDto;
import com.turkcell.rentACar.entities.dtos.list.CorporateCustomerListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/corporateCustomers")
public class CorporateCustomersController {

    private CorporateCustomerService corporateCustomerService;

    public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
        this.corporateCustomerService = corporateCustomerService;
    }

    @GetMapping("getAll")
    public DataResult<List<CorporateCustomerListDto>> getAll(){
        return this.corporateCustomerService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<GetCorporateCustomerDto> getById(@RequestParam int userId) throws BusinessException {
        return this.corporateCustomerService.getById(userId);
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException{
        return this.corporateCustomerService.add(createCorporateCustomerRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException{
        return this.corporateCustomerService.update(updateCorporateCustomerRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int userId) throws BusinessException{
        return this.corporateCustomerService.delete(userId);
    }
}
