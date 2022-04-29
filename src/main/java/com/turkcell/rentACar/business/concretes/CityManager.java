package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.CityDao;
import com.turkcell.rentACar.entities.dtos.get.GetCityDto;
import com.turkcell.rentACar.entities.dtos.list.CityListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCityRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateCityRequest;
import com.turkcell.rentACar.entities.sourceEntities.City;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class CityManager implements CityService {

    private final ModelMapperService modelMapperService;
    private final CityDao cityDao;

    public CityManager(ModelMapperService modelMapperService, CityDao cityDao) {
        this.modelMapperService = modelMapperService;
        this.cityDao = cityDao;
    }

    @Override
    public DataResult<List<CityListDto>> getAll() {

        List<City> cities = this.cityDao.findAll();
        List<CityListDto> result = cities.stream()
                .map(city -> this.modelMapperService.forDto().map(city,CityListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<GetCityDto> getById(int cityId) throws BusinessException {

        isExistsByCityId(cityId);

        City city = this.cityDao.getById(cityId);
        GetCityDto result = this.modelMapperService.forDto().map(city,GetCityDto.class);

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_GET);
    }

    @Override
    public Result add(CreateCityRequest createCityRequest) throws BusinessException {

        isCityExistsByCityName(createCityRequest.getCityName().toLowerCase(Locale.ROOT));

        City city = this.modelMapperService.forRequest().map(createCityRequest,City.class);
        city.setCityId(createCityRequest.getPlateNo());
        city.setCityName(createCityRequest.getCityName().toUpperCase(Locale.ROOT));
        this.cityDao.save(city);

        return new SuccessResult(BusinessMessages.SUCCESS_ADD);
    }

    @Override
    public Result update(UpdateCityRequest updateCityRequest) throws BusinessException {

        isExistsByCityId(updateCityRequest.getPlateNo());

        City city = this.modelMapperService.forRequest().map(updateCityRequest,City.class);
        city.setCityId(updateCityRequest.getPlateNo());
        city.setCityName(updateCityRequest.getCityName());
        this.cityDao.save(city);

        return new SuccessResult(BusinessMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result delete(int cityId) throws BusinessException {

        isExistsByCityId(cityId);

        this.cityDao.deleteById(cityId);

        return new SuccessResult(BusinessMessages.SUCCESS_DELETE);
    }

    @Override
    public void isExistsByCityId(int cityId) throws BusinessException {
        if(!this.cityDao.existsById(cityId)){
            throw new BusinessException(BusinessMessages.ERROR_CITY_NOT_FOUND);
        }
    }

    public void isCityExistsByCityName(String cityName) throws BusinessException {

        if(this.cityDao.existsByCityName(cityName.toLowerCase(Locale.ROOT))){
            throw new BusinessException(BusinessMessages.ERROR_CITY_ALREADY_EXISTS);
        }
    }
}
