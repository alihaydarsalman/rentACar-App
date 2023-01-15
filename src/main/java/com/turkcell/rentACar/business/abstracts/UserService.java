package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;

public interface UserService {

    void isExistsUserByUserId(int userId) throws BusinessException;
    void isExistsUserByEmail(String email) throws BusinessException;
}
