package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.dtos.get.GetBrandDto;
import com.turkcell.rentACar.entities.dtos.list.BrandListDto;
import com.turkcell.rentACar.entities.requests.create.CreateBrandRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateBrandRequest;
import com.turkcell.rentACar.entities.sourceEntities.Brand;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.BrandDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandManager implements BrandService {

    private final ModelMapperService modelMapperService;
    private final BrandDao brandDao;
    private final CarService carService;

    public BrandManager(ModelMapperService modelMapperService, BrandDao brandDao
    ,CarService carService) {
        this.modelMapperService = modelMapperService;
        this.brandDao = brandDao;
        this.carService=carService;
    }

    @Override
    public DataResult<List<BrandListDto>> getAll() {

        List<Brand> brands = this.brandDao.findAll();

        List<BrandListDto> result = brands.stream()
                .map(brand -> this.modelMapperService.forDto().map(brand,BrandListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {

        isExistsByBrandName(createBrandRequest.getBrandName());

        Brand brand=this.modelMapperService.forRequest().map(createBrandRequest,Brand.class);
        brand.setBrandId(0);
        this.brandDao.save(brand);

        return new SuccessResult(BusinessMessages.SUCCESS_ADD);
    }

    @Override
    public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {

        isExistsByBrandId(updateBrandRequest.getBrandId());
        isExistsByBrandName(updateBrandRequest.getBrandName());

        Brand brand=this.modelMapperService.forRequest().map(updateBrandRequest,Brand.class);
        this.brandDao.save(brand);

        return new SuccessResult(BusinessMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result delete(int brandId) throws BusinessException {

        isExistsByBrandId(brandId);
        this.carService.isExistsBrandByBrandId(brandId);
        this.brandDao.deleteById(brandId);

        return new SuccessResult(BusinessMessages.SUCCESS_DELETE);
    }

    @Override
    public DataResult<GetBrandDto> getById(int brandId) throws BusinessException {

        isExistsByBrandId(brandId);

        Brand brand = this.brandDao.getById(brandId);
        GetBrandDto getBrandDto = this.modelMapperService.forDto().map(brand,GetBrandDto.class);

        return new SuccessDataResult<>(getBrandDto,BusinessMessages.SUCCESS_GET);
    }

    private void isExistsByBrandName(String brandName) throws BusinessException {
        if(this.brandDao.existsByBrandName(brandName)){
            throw new BusinessException(BusinessMessages.ERROR_BRAND_NAME_EXISTS);
        }
    }

    @Override
    public void isExistsByBrandId(int brandId) throws BusinessException{
        if(!this.brandDao.existsByBrandId(brandId)){
            throw new BusinessException(BusinessMessages.ERROR_BRAND_NOT_FOUND);
        }
    }
}
