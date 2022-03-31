package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.dataAccess.CityDao;
import org.springframework.stereotype.Service;

@Service
public class CityManager implements CityService {

    private ModelMapperService modelMapperService;
    private CityDao cityDao;

    public CityManager(ModelMapperService modelMapperService, CityDao cityDao) {
        this.modelMapperService = modelMapperService;
        this.cityDao = cityDao;
    }

    @Override
    public void isExistsByCityId(int cityId) throws BusinessException {
        if(!this.cityDao.existsById(cityId)){
            throw new BusinessException(BusinessMessages.ERROR_CITY_NOT_FOUND);
        }
    }
}
