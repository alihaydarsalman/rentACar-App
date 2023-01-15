package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.dtos.get.GetCustomerDto;
import com.turkcell.rentACar.entities.dtos.list.CustomerListDto;
import com.turkcell.rentACar.business.abstracts.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {

    private final CustomerService customerService;

    public CustomersController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CustomerListDto>> getAll(){
        return this.customerService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<GetCustomerDto> getById(int id) throws BusinessException {
        return this.customerService.getById(id);
    }
}
