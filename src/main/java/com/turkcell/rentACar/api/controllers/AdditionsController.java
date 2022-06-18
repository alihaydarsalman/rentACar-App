package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.AdditionService;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetAdditionDto;
import com.turkcell.rentACar.entities.dtos.list.AdditionListDto;
import com.turkcell.rentACar.entities.requests.create.CreateAdditionRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateAdditionRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/additions")
public class AdditionsController {

    private AdditionService additionService;

    public AdditionsController(AdditionService additionService) {
        this.additionService = additionService;
    }

    @GetMapping("/getAll")
    public DataResult<List<AdditionListDto>> getAll(){
        return this.additionService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<GetAdditionDto> getById(@RequestParam int additionId) throws BusinessException{
        return this.additionService.getById(additionId);
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateAdditionRequest createAdditionRequest) throws BusinessException{
        return this.additionService.add(createAdditionRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateAdditionRequest updateAdditionRequest) throws BusinessException{
        return this.additionService.update(updateAdditionRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestParam int additionId) throws BusinessException{
        return this.additionService.delete(additionId);
    }
}
