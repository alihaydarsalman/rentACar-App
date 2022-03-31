package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.AdditionService;
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
import com.turkcell.rentACar.dataAccess.RentalDao;
import com.turkcell.rentACar.entities.dtos.get.GetRentalDto;
import com.turkcell.rentACar.entities.dtos.list.RentalListDto;
import com.turkcell.rentACar.entities.requests.create.CreateRentalRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateRentalRequest;
import com.turkcell.rentACar.entities.sourceEntities.Addition;
import com.turkcell.rentACar.entities.sourceEntities.Rental;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalManager implements RentalService {

    private final ModelMapperService modelMapperService;
    private final RentalDao rentalDao;
    private CarService carService;
    private CarMaintenanceService carMaintenanceService;
    private AdditionService additionService;

    public RentalManager(ModelMapperService modelMapperService, RentalDao rentalDao, CarService carService
                        , @Lazy CarMaintenanceService carMaintenanceService
                        , AdditionService additionService) {
        this.modelMapperService = modelMapperService;
        this.rentalDao = rentalDao;
        this.carService = carService;
        this.carMaintenanceService=carMaintenanceService;
        this.additionService=additionService;
    }

    @Override
    public DataResult<List<RentalListDto>> getAll() {

        List<Rental> rentals=this.rentalDao.findAll();
        List<RentalListDto> result=rentals.stream()
                .map(rental -> this.modelMapperService.forDto().map(rental,RentalListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public Result add(CreateRentalRequest createRentalRequest) throws BusinessException {

        this.carService.isExistsByCarId(createRentalRequest.getCarId());
        this.carMaintenanceService.isCarUnderMaintenance(createRentalRequest.getCarId());
        areDatesValid(createRentalRequest.getRentDate());
        isRentDateAfterReturnDate(createRentalRequest.getRentDate(),createRentalRequest.getRentReturnDate());
        isCarCanRented(createRentalRequest);
        List<Addition> tempAdditions = new ArrayList<>();

        for (Integer addId: createRentalRequest.getAdditionId()){
            this.additionService.isExistsByAdditionId(addId);
            Addition addition = this.additionService.getAdditionById(addId);
            tempAdditions.add(addition);
        }

        Rental rental = this.modelMapperService.forRequest().map(createRentalRequest,Rental.class);
        rental.setRentId(0);
        rental.setAdditions(tempAdditions);
        this.rentalDao.save(rental);

        return new SuccessResult(BusinessMessages.SUCCESS_ADD);
    }

    @Override
    public Result update(UpdateRentalRequest updateRentalRequest) throws BusinessException {

        isExistsByRentalId(updateRentalRequest.getRentId());
        this.carService.isExistsByCarId(updateRentalRequest.getCarId());
        this.carMaintenanceService.isCarUnderMaintenance(updateRentalRequest.getCarId());
        areDatesValid(updateRentalRequest.getRentDate());
        isRentDateAfterReturnDate(updateRentalRequest.getRentDate(),updateRentalRequest.getRentReturnDate());
        isCarCanRented(updateRentalRequest);
        List<Addition> tempAdditions = new ArrayList<>();

        for (Integer addId: updateRentalRequest.getAdditionId()){
            this.additionService.isExistsByAdditionId(addId);
            Addition addition = this.additionService.getAdditionById(addId);
            tempAdditions.add(addition);
        }

        Rental rental=this.modelMapperService.forRequest().map(updateRentalRequest,Rental.class);
        rental.setAdditions(tempAdditions);
        this.rentalDao.save(rental);


        return new SuccessResult(BusinessMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result delete(int rentId) throws BusinessException {

        isExistsByRentalId(rentId);

        this.rentalDao.deleteById(rentId);

        return new SuccessResult(BusinessMessages.SUCCESS_DELETE);
    }

    @Override
    public DataResult<GetRentalDto> getById(int rentId) throws BusinessException {

        isExistsByRentalId(rentId);

        Rental rental = this.rentalDao.getById(rentId);
        GetRentalDto result=this.modelMapperService.forDto().map(rental,GetRentalDto.class);

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_GET);
    }

    @Override
    public DataResult<List<RentalListDto>> getByCarId(int carId) throws BusinessException {

        this.carService.isExistsByCarId(carId);
        isExistsByCarIdOnRentalTable(carId);

        List<Rental> rentals=this.rentalDao.findAllByCar_CarId(carId);
        List<RentalListDto> result=rentals.stream()
                .map(rental -> this.modelMapperService.forDto().map(rental,RentalListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST);
    }

    public void isExistsByRentalId(int rentId) throws BusinessException {
        if (!this.rentalDao.existsById(rentId)){
            throw new BusinessException(BusinessMessages.ERROR_RENTAL_NOT_FOUND);
        }
    }

    public void isExistsByCarIdOnRentalTable(int carId) throws BusinessException{
        if(!this.rentalDao.existsByCar_CarId(carId)){
            throw new BusinessException(BusinessMessages.ERROR_RENTAL_NOT_FOUND_BY_CAR_ID);
        }
    }

    @Override
    public void isCarStillRented(int carId) throws BusinessException{
        List<Rental> rentals = this.rentalDao.getRentalByCar_CarId(carId);
        for(Rental rental:rentals){
            if(rental.getRentReturnDate().isAfter(LocalDate.now())){
                throw new BusinessException(BusinessMessages.ERROR_CAR_STILL_RENTED);
            }
        }
    }

    private void areDatesValid(LocalDate rentDate) throws BusinessException {
        if(rentDate.isBefore(LocalDate.now())){
            throw new BusinessException(BusinessMessages.ERROR_INVALID_DATES);
        }
    }

    private void isRentDateAfterReturnDate(LocalDate rentDate, LocalDate returnDate) throws BusinessException {
        if (rentDate.isAfter(returnDate)){
            throw new BusinessException(BusinessMessages.ERROR_RENT_DATE_CANNOT_AFTER_RETURN_DATE);
        }
    }


    private void isCarCanRented(CreateRentalRequest createRentalRequest) throws BusinessException{
        List<Rental> rentals = this.rentalDao.getRentalByCar_CarId(createRentalRequest.getCarId());
        for(Rental rental:rentals){
            if (createRentalRequest.getRentDate().isBefore(rental.getRentDate())){
                if(createRentalRequest.getRentReturnDate().isAfter(rental.getRentReturnDate())){
                    throw new BusinessException(BusinessMessages.ERROR_CAR_ALREADY_RENTED);
                }
                if ( (createRentalRequest.getRentReturnDate().isBefore(rental.getRentReturnDate())) || (createRentalRequest.getRentReturnDate().isEqual(rental.getRentReturnDate()))){

                    if( (createRentalRequest.getRentReturnDate().isAfter(rental.getRentDate())) || (createRentalRequest.getRentReturnDate().isEqual(rental.getRentDate()))){
                        throw new BusinessException(BusinessMessages.ERROR_CAR_ALREADY_RENTED);
                    }
                }
            }
            if( (createRentalRequest.getRentDate().isAfter(rental.getRentDate()) || createRentalRequest.getRentDate().isEqual(rental.getRentDate()))) {
                if ( (createRentalRequest.getRentDate().isBefore(rental.getRentReturnDate())) || (createRentalRequest.getRentDate().isEqual(rental.getRentReturnDate()))){
                    if( (createRentalRequest.getRentReturnDate().isAfter(rental.getRentReturnDate())) ||
                            (createRentalRequest.getRentReturnDate().isBefore(rental.getRentReturnDate()))){
                        throw new BusinessException(BusinessMessages.ERROR_CAR_ALREADY_RENTED);
                    }
                }
            }
        }
    }


    private void isCarCanRented(UpdateRentalRequest updateRentalRequest) throws BusinessException{
        List<Rental> rentals = this.rentalDao.getRentalByCar_CarId(updateRentalRequest.getCarId());
        for(Rental rental:rentals){
            if (updateRentalRequest.getRentDate().isBefore(rental.getRentDate())){
                if((updateRentalRequest.getRentReturnDate().isAfter(rental.getRentReturnDate())) &&  (rental.getRentId() != updateRentalRequest.getRentId())){
                    throw new BusinessException(BusinessMessages.ERROR_CAR_ALREADY_RENTED);
                }
                if (updateRentalRequest.getRentReturnDate().isBefore(rental.getRentReturnDate())){

                    if( (updateRentalRequest.getRentReturnDate().isAfter(rental.getRentDate())) &&  (rental.getRentId() != updateRentalRequest.getRentId())){
                        throw new BusinessException(BusinessMessages.ERROR_CAR_ALREADY_RENTED);
                    }
                }
            }
            if(updateRentalRequest.getRentDate().isAfter(rental.getRentDate())) {
                if (updateRentalRequest.getRentDate().isBefore(rental.getRentReturnDate())){
                    if( (updateRentalRequest.getRentReturnDate().isAfter(rental.getRentReturnDate())) ||
                            (updateRentalRequest.getRentReturnDate().isBefore(rental.getRentReturnDate()))
                    &&  (rental.getRentId() != updateRentalRequest.getRentId())){
                        throw new BusinessException(BusinessMessages.ERROR_CAR_ALREADY_RENTED);
                    }
                }
            }
        }
    }

}
