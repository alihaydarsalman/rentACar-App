package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;

public interface CityService {

    void isExistsByCityId(int cityId) throws BusinessException;
}
