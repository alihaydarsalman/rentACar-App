package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.dataAccess.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements UserService {

    private final UserDao userDao;

    public UserManager(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void isExistsUserByUserId(int userId) throws BusinessException {
        if(!this.userDao.existsByUserId(userId)){
            throw new BusinessException(BusinessMessages.ERROR_USER_NOT_FOUND);
        }
    }

    @Override
    public void isExistsUserByEmail(String email) throws BusinessException {
        if (this.userDao.existsUserByEmail(email)){
            throw new BusinessException(BusinessMessages.ERROR_EMAIL_ALREADY_EXISTS);
        }
    }


}
