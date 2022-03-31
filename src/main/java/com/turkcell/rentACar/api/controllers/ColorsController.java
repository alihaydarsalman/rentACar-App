package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetColorDto;
import com.turkcell.rentACar.entities.dtos.list.ColorListDto;
import com.turkcell.rentACar.entities.requests.create.CreateColorRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateColorRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {

    private ColorService colorService;

    public ColorsController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping("/getAll")
    public DataResult<List<ColorListDto>> getAll()
    {
        return this.colorService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateColorRequest createColorRequest) throws BusinessException
    {
        return this.colorService.add(createColorRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateColorRequest updateColorRequest) throws BusinessException
    {
        return this.colorService.update(updateColorRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int colorId) throws BusinessException
    {
        return this.colorService.delete(colorId);
    }

    @GetMapping("/getById")
    public DataResult<GetColorDto> getById(@RequestParam int colorId) throws BusinessException
    {
        return this.colorService.getById(colorId);
    }
}
