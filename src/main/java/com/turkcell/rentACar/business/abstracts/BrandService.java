package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetBrandDto;
import com.turkcell.rentACar.entities.dtos.list.BrandListDto;
import com.turkcell.rentACar.entities.requests.create.CreateBrandRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateBrandRequest;


import java.util.List;

public interface BrandService {

    DataResult<List<BrandListDto>> getAll();

    Result add(CreateBrandRequest createBrandRequest) throws BusinessException;

    Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;

    Result delete(int brandId) throws BusinessException;

    DataResult<GetBrandDto> getById(int brandId) throws BusinessException;

    void isExistsByBrandId(int brandId) throws BusinessException;
}
