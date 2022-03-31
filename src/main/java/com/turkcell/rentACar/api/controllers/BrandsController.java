package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetBrandDto;
import com.turkcell.rentACar.entities.dtos.list.BrandListDto;
import com.turkcell.rentACar.entities.requests.create.CreateBrandRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateBrandRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {

    private BrandService brandService;

    public BrandsController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/getAll")
    public DataResult<List<BrandListDto>> getAll()
    {
        return this.brandService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateBrandRequest createBrandRequest) throws BusinessException
    {
        return this.brandService.add(createBrandRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest) throws BusinessException
    {
        return this.brandService.update(updateBrandRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int brandId) throws BusinessException
    {
        return this.brandService.delete(brandId);
    }

    @GetMapping("getById")
    public DataResult<GetBrandDto> getById(@RequestParam int brandId) throws BusinessException
    {
        return this.brandService.getById(brandId);
    }
}
