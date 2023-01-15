package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.dataAccess.CarDao;
import com.turkcell.rentACar.entities.dtos.get.GetCarDto;
import com.turkcell.rentACar.entities.dtos.list.CarListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCarRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateCarRequest;
import com.turkcell.rentACar.entities.sourceEntities.Car;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarManager implements CarService {

    private final ModelMapperService modelMapperService;
    private final CarDao carDao;
    private final ColorService colorService;
    private final BrandService brandService;
    private final CarMaintenanceService carMaintenanceService;
    private final RentalService rentalService;

    public CarManager(ModelMapperService modelMapperService, CarDao carDao
            , @Lazy ColorService colorService, @Lazy BrandService brandService
            , @Lazy CarMaintenanceService carMaintenanceService
            , @Lazy RentalService rentalService) {
        this.modelMapperService = modelMapperService;
        this.carDao = carDao;
        this.colorService = colorService;
        this.brandService = brandService;
        this.carMaintenanceService = carMaintenanceService;
        this.rentalService=rentalService;
    }

    @Override
    public DataResult<List<CarListDto>> getAll() {

        List<Car> cars = this.carDao.findAll();
        List<CarListDto> result = cars.stream()
                .map(car -> this.modelMapperService.forDto().map(car,CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public Result add(CreateCarRequest createCarRequest) throws BusinessException {

        this.brandService.isExistsByBrandId(createCarRequest.getBrandId());
        this.colorService.isExistsByColorId(createCarRequest.getColorId());

        Car car = this.modelMapperService.forRequest().map(createCarRequest,Car.class);
        car.setCarId(0);
        this.carDao.save(car);

        return new SuccessResult(BusinessMessages.SUCCESS_ADD);
    }

    @Override
    public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {

        isExistsByCarId(updateCarRequest.getCarId());
        this.brandService.isExistsByBrandId(updateCarRequest.getBrandId());
        this.colorService.isExistsByColorId(updateCarRequest.getColorId());

        Car car = this.modelMapperService.forRequest().map(updateCarRequest,Car.class);
        this.carDao.save(car);

        return new SuccessResult(BusinessMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result delete(int carId) throws BusinessException {

        isExistsByCarId(carId);
        this.carMaintenanceService.isExistsCarByCarId(carId);

        this.carDao.deleteById(carId);

        return new SuccessResult(BusinessMessages.SUCCESS_DELETE);
    }

    @Override
    public DataResult<GetCarDto> getById(int carId) throws BusinessException {

        isExistsByCarId(carId);

        Car car = this.carDao.getById(carId);
        GetCarDto result = this.modelMapperService.forDto().map(car,GetCarDto.class);

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_GET);
    }

    @Override
    public Car getCarByCarId(int carId) throws BusinessException {
        isExistsByCarId(carId);
        return this.carDao.getById(carId);
    }

    @Override
    public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo-1,pageSize);

        List<Car> cars = carDao.findAll(pageable).getContent();
        List<CarListDto> result = cars.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST_PAGED);
    }

    @Override
    public DataResult<List<CarListDto>> getByDailyPriceLessThanEqual(double dailyPrice) {

        List<Car> cars = this.carDao.findAllByDailyPriceLessThanEqual(dailyPrice);
        List<CarListDto> result=cars.stream()
                .map(car -> this.modelMapperService.forDto().map(car,CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<List<CarListDto>> getByDailyPriceGreaterThanEqual(double dailyPrice) {

        List<Car> cars = this.carDao.findAllByDailyPriceGreaterThanEqual(dailyPrice);
        List<CarListDto> result = cars.stream()
                .map(car -> this.modelMapperService.forDto().map(car,CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<List<CarListDto>> getSortedCarsByDailyPrice(Sort.Direction sortDirection) {

        Sort sort = Sort.by(sortDirection,"dailyPrice");
        List<Car> cars = this.carDao.findAll(sort);
        List<CarListDto> result = cars.stream()
                .map(car -> this.modelMapperService.forDto().map(car,CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<List<CarListDto>> getAllByColorId(int colorId) throws BusinessException {

        this.colorService.isExistsByColorId(colorId);

        List<Car> cars = this.carDao.findAllByColor_ColorId(colorId);
        List<CarListDto> result=cars.stream()
                .map(car -> this.modelMapperService.forDto().map(car,CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<List<CarListDto>> getAllByBrandId(int branId) throws BusinessException {

        this.brandService.isExistsByBrandId(branId);

        List<Car> cars = this.carDao.findAllByBrand_BrandId(branId);
        List<CarListDto> result=cars.stream()
                .map(car -> this.modelMapperService.forDto().map(car,CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST);
    }


    @Override
    public DataResult<List<CarListDto>> getAllByModelYear(int modelYear) {

        List<Car> cars= this.carDao.findAllByModelYear(modelYear);
        List<CarListDto> result=cars.stream()
                .map(car -> this.modelMapperService.forDto().map(car,CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public void updateCurrentKilometer(int carId, double returnKilometer) throws BusinessException {

        isExistsByCarId(carId);

        Car car = this.carDao.getById(carId);

        isCurrentKilometerGreaterThanReturnKilometer(car.getCurrentKilometer(), returnKilometer);

        car.setCurrentKilometer(returnKilometer);

        this.carDao.save(car);
    }

    @Override
    public void isExistsByCarId(int carId) throws BusinessException {
        if(!this.carDao.existsByCarId(carId)) {
            throw new BusinessException(BusinessMessages.ERROR_CAR_NOT_FOUND);
        }
    }

    @Override
    public void isExistsBrandByBrandId(int brandId) throws BusinessException {
        if (this.carDao.existsByBrand_BrandId(brandId)){
            throw new BusinessException(BusinessMessages.ERROR_BRAND_CANNOT_DELETE);
        }
    }

    @Override
    public void isExistsColorByColorId(int colorId) throws BusinessException{
        if(this.carDao.existsByColor_ColorId(colorId)){
            throw new BusinessException(BusinessMessages.ERROR_COLOR_CANNOT_DELETE);
        }
    }

    private void isCurrentKilometerGreaterThanReturnKilometer(double startKilometer, double endKilometer) throws BusinessException {
        if(startKilometer>=endKilometer){
            throw new BusinessException(BusinessMessages.ERROR_INVALID_KILOMETER_INFO);
        }
    }
}
