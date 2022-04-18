package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.RentalService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.CarMaintenanceDao;
import com.turkcell.rentACar.entities.dtos.get.GetCarMaintenanceDto;
import com.turkcell.rentACar.entities.dtos.list.CarMaintenanceListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.entities.sourceEntities.CarMaintenance;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

    private final ModelMapperService modelMapperService;
    private final CarMaintenanceDao carMaintenanceDao;
    private CarService carService;
    private RentalService rentalService;

    public CarMaintenanceManager(ModelMapperService modelMapperService, CarMaintenanceDao carMaintenanceDao, CarService carService
                                ,RentalService rentalService) {
        this.modelMapperService = modelMapperService;
        this.carMaintenanceDao = carMaintenanceDao;
        this.carService = carService;
        this.rentalService=rentalService;
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getAll() {

        List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findAll();
        List<CarMaintenanceListDto> result = carMaintenances.stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance,CarMaintenanceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {

        isMaintenanceReturnDateBeforeNow(createCarMaintenanceRequest.getMaintenanceReturnDate());
        isCarStillUnderMaintenance(createCarMaintenanceRequest.getCarId());
        this.carService.isExistsByCarId(createCarMaintenanceRequest.getCarId());
        this.rentalService.isCarStillRented(createCarMaintenanceRequest.getCarId());

        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult(BusinessMessages.SUCCESS_ADD);
    }

    @Override
    public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {

        isMaintenanceReturnDateBeforeNow(updateCarMaintenanceRequest.getMaintenanceReturnDate());
        isExistsByMaintenanceId(updateCarMaintenanceRequest.getMaintenanceId());
        isCarAlreadyReturnFromMaintenance(updateCarMaintenanceRequest.getMaintenanceId());
        this.carService.isExistsByCarId(updateCarMaintenanceRequest.getCarId());
        this.rentalService.isCarStillRented(updateCarMaintenanceRequest.getCarId());

        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,CarMaintenance.class);
        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult(BusinessMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result delete(int maintenanceId) throws BusinessException{

        isExistsByMaintenanceId(maintenanceId);
        isCarStillUnderMaintenance(maintenanceId);

        this.carMaintenanceDao.deleteById(maintenanceId);

        return new SuccessResult(BusinessMessages.SUCCESS_DELETE);
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getByCarId(int carId) throws BusinessException {

        this.carService.isExistsByCarId(carId);
        isExistsByCarIdOnMaintenanceTable(carId);

        List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findCarMaintenanceByCar_CarId(carId);
        List<CarMaintenanceListDto> result = carMaintenances.stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance,CarMaintenanceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<GetCarMaintenanceDto> getById(int maintenanceId) throws BusinessException {

        isExistsByMaintenanceId(maintenanceId);

        CarMaintenance carMaintenance = this.carMaintenanceDao.getById(maintenanceId);
        GetCarMaintenanceDto result = this.modelMapperService.forDto().map(carMaintenance, GetCarMaintenanceDto.class);

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_GET);
    }

    @Override
    public void isExistsByMaintenanceId(int maintenanceId) throws BusinessException {
        if(!this.carMaintenanceDao.existsById(maintenanceId)) {
            throw new BusinessException(BusinessMessages.ERROR_MAINTENANCE_NOT_FOUND);
        }
    }

    private void isExistsByCarIdOnMaintenanceTable(int carId) throws BusinessException{
        if(!this.carMaintenanceDao.existsByCar_CarId(carId)){
            throw new BusinessException(BusinessMessages.ERROR_MAINTENANCE_NOT_FOUND_BY_CAR_ID);
        }
    }

    private void isMaintenanceReturnDateBeforeNow(LocalDate returnDate) throws BusinessException {
        if (returnDate != null){
            if(returnDate.isBefore(LocalDate.now())){
                throw new BusinessException(BusinessMessages.ERROR_MAINTENANCE_RETURN_DATE_CANNOT_BEFORE_NOW);
            }
        }
    }

    private void isCarAlreadyReturnFromMaintenance(int maintenanceId) throws BusinessException {
        CarMaintenance carMaintenance= this.carMaintenanceDao.getById(maintenanceId);
        if (carMaintenance.getMaintenanceReturnDate() != null){
            if(carMaintenance.getMaintenanceReturnDate().isBefore(LocalDate.now())){
                throw new BusinessException(BusinessMessages.ERROR_CAR_RETURNED_FROM_MAINTENANCE_CANNOT_UPDATE);
            }
        }
    }

    @Override
    public void isExistsCarByCarId(int carId) throws BusinessException {
        if(this.carMaintenanceDao.existsByCar_CarId(carId)){
            throw new BusinessException(BusinessMessages.ERROR_CAR_CANNOT_DELETE);
        }
    }

    private void isCarStillUnderMaintenance(int carId) throws BusinessException{
        List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findCarMaintenanceByCar_CarId(carId);
        for(CarMaintenance carMaintenance:carMaintenances) {
            if (carMaintenance != null){
                if (carMaintenance.getMaintenanceReturnDate()==null || carMaintenance.getMaintenanceReturnDate().isAfter(LocalDate.now())) {
                    throw new BusinessException(BusinessMessages.ERROR_CAR_ALREADY_UNDER_MAINTENANCE);
                }
            }
        }
    }

    @Override
    public void isCarUnderMaintenanceForRental(int carId, LocalDate rentDate) throws BusinessException {
        List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findCarMaintenanceByCar_CarId(carId);

        if (carMaintenances != null){
            for (CarMaintenance carMaintenance: carMaintenances){
                if (carMaintenance.getMaintenanceReturnDate() == null){
                    throw new BusinessException(BusinessMessages.ERROR_CAR_ALREADY_UNDER_MAINTENANCE_UNKNOWN_RETURN_DATE);
                }
                else{
                    if (carMaintenance.getMaintenanceReturnDate().isAfter(rentDate) || carMaintenance.getMaintenanceReturnDate().isEqual(rentDate)){
                        throw new BusinessException(BusinessMessages.ERROR_CAR_ALREADY_UNDER_MAINTENANCE_CANNOT_RENTED + carMaintenance.getMaintenanceReturnDate());
                    }
                }
            }
        }
    }

}