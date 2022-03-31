package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CityService;
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
}
