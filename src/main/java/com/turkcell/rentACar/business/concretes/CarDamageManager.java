package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarDamageService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.CarDamageDao;
import com.turkcell.rentACar.entities.dtos.get.GetCarDamageDto;
import com.turkcell.rentACar.entities.dtos.list.CarDamageListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCarDamageRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateCarDamageRequest;
import com.turkcell.rentACar.entities.sourceEntities.CarDamage;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarDamageManager implements CarDamageService {

    private final ModelMapperService modelMapperService;
    private final CarDamageDao carDamageDao;
    private final CarService carService;

    public CarDamageManager(ModelMapperService modelMapperService, CarDamageDao carDamageDao, CarService carService) {
        this.modelMapperService = modelMapperService;
        this.carDamageDao = carDamageDao;
        this.carService = carService;
    }

    @Override
    public DataResult<List<CarDamageListDto>> getAll() {

        List<CarDamage> carDamages = this.carDamageDao.findAll();
        List<CarDamageListDto> result = carDamages.stream()
                .map(carDamage -> this.modelMapperService.forDto().map(carDamage,CarDamageListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<GetCarDamageDto> getById(int damageId) throws BusinessException {

        isDamageExistsByDamageId(damageId);

        CarDamage carDamage = this.carDamageDao.getById(damageId);
        GetCarDamageDto result = this.modelMapperService.forDto().map(carDamage,GetCarDamageDto.class);

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_GET);
    }

    @Override
    public DataResult<List<CarDamageListDto>> getDamagesByCar(int carId) throws BusinessException {

        this.carService.isExistsByCarId(carId);
        isDamageExistsByCar(carId);

        List<CarDamage> carDamages = this.carDamageDao.findAllByCar_CarId(carId);
        List<CarDamageListDto> result = carDamages.stream()
                .map(carDamage -> this.modelMapperService.forDto().map(carDamage, CarDamageListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public Result add(CreateCarDamageRequest createCarDamageRequest) throws BusinessException {

        this.carService.isExistsByCarId(createCarDamageRequest.getCarId());

        CarDamage carDamage = this.modelMapperService.forRequest().map(createCarDamageRequest,CarDamage.class);
        carDamage.setDamageId(0);
        this.carDamageDao.save(carDamage);

        return new SuccessResult(BusinessMessages.SUCCESS_ADD);
    }

    @Override
    public Result update(UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException {

        isDamageExistsByDamageId(updateCarDamageRequest.getDamageId());
        this.carService.isExistsByCarId(updateCarDamageRequest.getCarId());

        CarDamage carDamage = this.modelMapperService.forRequest().map(updateCarDamageRequest,CarDamage.class);
        this.carDamageDao.save(carDamage);

        return new SuccessResult(BusinessMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result delete(int damageId) throws BusinessException {

        isDamageExistsByDamageId(damageId);

        this.carDamageDao.deleteById(damageId);

        return new SuccessResult(BusinessMessages.SUCCESS_DELETE);
    }


    private void isDamageExistsByDamageId(int damageId) throws BusinessException {
        if(!this.carDamageDao.existsById(damageId)){
            throw new BusinessException(BusinessMessages.ERROR_DAMAGE_NOT_FOUND);
        }
    }

    private void isDamageExistsByCar(int carId) throws BusinessException {
        if(!this.carDamageDao.existsByCar_CarId(carId)){
            throw new BusinessException(BusinessMessages.DAMAGE_NOT_FOUND_BY_CAR);
        }
    }
}
