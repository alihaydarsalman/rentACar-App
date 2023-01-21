package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.dataAccess.RentalDao;
import com.turkcell.rentACar.entities.converters.RentalConverter;
import com.turkcell.rentACar.entities.dtos.get.GetRentalDto;
import com.turkcell.rentACar.entities.dtos.list.AdditionListDto;
import com.turkcell.rentACar.entities.requests.create.CreateRentalRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateRentalRequest;
import com.turkcell.rentACar.entities.sourceEntities.Rental;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.entities.sourceEntities.Addition;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final CarService carService;
    private final CarMaintenanceService carMaintenanceService;
    private final AdditionService additionService;
    private final CityService cityService;
    private final IndividualCustomerService individualCustomerService;
    private final CorporateCustomerService corporateCustomerService;
    private final RentalConverter rentalConverter;

    @Autowired
    public RentalManager(ModelMapperService modelMapperService, RentalDao rentalDao, CarService carService
            , @Lazy CarMaintenanceService carMaintenanceService
            , AdditionService additionService, CityService cityService
            , IndividualCustomerService individualCustomerService, CorporateCustomerService corporateCustomerService
            , RentalConverter rentalConverter) {
        this.modelMapperService = modelMapperService;
        this.rentalDao = rentalDao;
        this.carService = carService;
        this.carMaintenanceService=carMaintenanceService;
        this.additionService=additionService;
        this.cityService = cityService;
        this.individualCustomerService = individualCustomerService;
        this.corporateCustomerService = corporateCustomerService;
        this.rentalConverter = rentalConverter;
    }

    @Override
    public DataResult<List<GetRentalDto>> getAll() {

        List<Rental> rentals=this.rentalDao.findAll();
        List<GetRentalDto> result = this.rentalConverter.convertRentalToDto(rentals);

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public Rental addRentalForIndividualCustomer(CreateRentalRequest createRentalRequest) throws BusinessException {

        this.carService.isExistsByCarId(createRentalRequest.getCarId());
        this.cityService.isExistsByCityId(createRentalRequest.getFromCityId());
        this.cityService.isExistsByCityId(createRentalRequest.getToCityId());
        this.carMaintenanceService.isCarUnderMaintenanceForRental(createRentalRequest.getCarId(),createRentalRequest.getRentDate());
        areDatesValid(createRentalRequest.getRentDate());
        isRentDateAfterReturnDate(createRentalRequest.getRentDate(),createRentalRequest.getRentReturnDate());
        isCarCanRented(createRentalRequest);
        this.individualCustomerService.isIndividualCustomerExistsById(createRentalRequest.getUserId());

        Rental rental = Rental.builder()
                .rentDate(createRentalRequest.getRentDate())
                .rentReturnDate(createRentalRequest.getRentReturnDate())
                .car(this.carService.getCarByCarId(createRentalRequest.getCarId()))
                .customer(this.individualCustomerService.getCustomerById(createRentalRequest.getUserId()))
                .fromCity(this.cityService.getCityById(createRentalRequest.getFromCityId()))
                .toCity(this.cityService.getCityById(createRentalRequest.getToCityId()))
                .build();

        setAdditionForRental(rental, createRentalRequest);

        this.rentalDao.save(rental);

        return rental;
    }

    @Override
    public Rental addRentalForCorporateCustomer(CreateRentalRequest createRentalRequest) throws BusinessException {

        this.carService.isExistsByCarId(createRentalRequest.getCarId());
        this.cityService.isExistsByCityId(createRentalRequest.getFromCityId());
        this.cityService.isExistsByCityId(createRentalRequest.getToCityId());
        this.carMaintenanceService.isCarUnderMaintenanceForRental(createRentalRequest.getCarId(),createRentalRequest.getRentDate());
        areDatesValid(createRentalRequest.getRentDate());
        isRentDateAfterReturnDate(createRentalRequest.getRentDate(),createRentalRequest.getRentReturnDate());
        isCarCanRented(createRentalRequest);
        this.corporateCustomerService.isCorporateCustomerExistsById(createRentalRequest.getUserId());

        Rental rental = Rental.builder()
                .rentDate(createRentalRequest.getRentDate())
                .rentReturnDate(createRentalRequest.getRentReturnDate())
                .car(this.carService.getCarByCarId(createRentalRequest.getCarId()))
                .customer(this.corporateCustomerService.getCustomerById(createRentalRequest.getUserId()))
                .fromCity(this.cityService.getCityById(createRentalRequest.getFromCityId()))
                .toCity(this.cityService.getCityById(createRentalRequest.getToCityId()))
                .build();

        setAdditionForRental(rental,createRentalRequest);

        this.rentalDao.save(rental);

        return rental;
    }


    @Override
    public Result updateRentalForIndividualCustomer(UpdateRentalRequest updateRentalRequest) throws BusinessException {

        isRentalExistsByRentalId(updateRentalRequest.getRentId());
        this.carService.isExistsByCarId(updateRentalRequest.getCarId());
        this.carMaintenanceService.isCarUnderMaintenanceForRental(updateRentalRequest.getCarId(),updateRentalRequest.getRentDate());
        areDatesValid(updateRentalRequest.getRentDate());
        isRentDateAfterReturnDate(updateRentalRequest.getRentDate(),updateRentalRequest.getRentReturnDate());
        isCarCanRented(updateRentalRequest);
        this.individualCustomerService.isIndividualCustomerExistsById(updateRentalRequest.getUserId());

        Rental rental = Rental.builder()
                .rentId(updateRentalRequest.getRentId())
                .rentDate(updateRentalRequest.getRentDate())
                .rentReturnDate(updateRentalRequest.getRentReturnDate())
                .car(this.carService.getCarByCarId(updateRentalRequest.getCarId()))
                .customer(this.individualCustomerService.getCustomerById(updateRentalRequest.getUserId()))
                .fromCity(this.cityService.getCityById(updateRentalRequest.getFromCityId()))
                .toCity(this.cityService.getCityById(updateRentalRequest.getToCityId()))
                .build();

        setAdditionForRental(rental, updateRentalRequest);

        this.rentalDao.save(rental);

        return new SuccessResult(BusinessMessages.SUCCESS_UPDATE);
    }


    @Override
    public Result updateRentalForCorporateCustomer(UpdateRentalRequest updateRentalRequest) throws BusinessException {

        isRentalExistsByRentalId(updateRentalRequest.getRentId());
        this.carService.isExistsByCarId(updateRentalRequest.getCarId());
        this.carMaintenanceService.isCarUnderMaintenanceForRental(updateRentalRequest.getCarId(),updateRentalRequest.getRentDate());
        areDatesValid(updateRentalRequest.getRentDate());
        isRentDateAfterReturnDate(updateRentalRequest.getRentDate(),updateRentalRequest.getRentReturnDate());
        isCarCanRented(updateRentalRequest);
        this.corporateCustomerService.isCorporateCustomerExistsById(updateRentalRequest.getUserId());

        Rental rental = Rental.builder()
                .rentId(updateRentalRequest.getRentId())
                .rentDate(updateRentalRequest.getRentDate())
                .rentReturnDate(updateRentalRequest.getRentReturnDate())
                .car(this.carService.getCarByCarId(updateRentalRequest.getCarId()))
                .customer(this.corporateCustomerService.getCustomerById(updateRentalRequest.getUserId()))
                .fromCity(this.cityService.getCityById(updateRentalRequest.getFromCityId()))
                .toCity(this.cityService.getCityById(updateRentalRequest.getToCityId()))
                .build();

        setAdditionForRental(rental, updateRentalRequest);

        this.rentalDao.save(rental);

        return new SuccessResult(BusinessMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deliverCar(int rentId, int carId) throws BusinessException {

        isRentalExistsByRentalId(rentId);
        isExistsByCarIdOnRentalTable(carId);
        this.carService.isExistsByCarId(carId);

        Rental rental = this.rentalDao.getById(rentId);

        rental.setRentKilometer(rental.getCar().getCurrentKilometer());

        this.rentalDao.save(rental);

        return new SuccessResult(BusinessMessages.SUCCESS_DELIVER);
    }

    @Override
    public Result receiveCar(int rentId, int carId, double returnKilometer) throws BusinessException {

        isRentalExistsByRentalId(rentId);
        isExistsByCarIdOnRentalTable(carId);

        Rental rental = this.rentalDao.getById(rentId);

        this.carService.updateCurrentKilometer(carId, returnKilometer);
        rental.setRentReturnKilometer(rental.getCar().getCurrentKilometer());
        this.rentalDao.save(rental);

        return new SuccessResult(BusinessMessages.SUCCESS_RECEIVE);
    }


    @Override
    public Result delete(int rentId) throws BusinessException {

        isRentalExistsByRentalId(rentId);

        this.rentalDao.deleteById(rentId);

        return new SuccessResult(BusinessMessages.SUCCESS_DELETE);
    }

    @Override
    public DataResult<List<AdditionListDto>> getOrdersByRent(int rentId) {

        isRentalExistsByRentalId(rentId);

        Rental rental = this.rentalDao.getById(rentId);

        List<Addition> additions = rental.getAdditionList();

        List<AdditionListDto> result = additions.stream()
                .map(addition -> this.modelMapperService.forDto().map(addition, AdditionListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult(result, BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<GetRentalDto> getById(int rentId) throws BusinessException {

        isRentalExistsByRentalId(rentId);

        Rental rental = this.rentalDao.getById(rentId);
        GetRentalDto rentalDto = this.rentalConverter.convertRentalToDto(rental);

        return new SuccessDataResult<>(rentalDto,BusinessMessages.SUCCESS_GET);
    }


    @Override
    public DataResult<List<GetRentalDto>> getByCarId(int carId) throws BusinessException {

        this.carService.isExistsByCarId(carId);
        isExistsByCarIdOnRentalTable(carId);

        List<Rental> rentals=this.rentalDao.findAllByCar_CarId(carId);
        List<GetRentalDto> result = this.rentalConverter.convertRentalToDto(rentals);

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST);
    }


    @Override
    public Rental getByRentalId(int rentId) throws BusinessException {

        isRentalExistsByRentalId(rentId);

        return this.rentalDao.findByRentId(rentId);
    }

    @Override
    public void isRentalExistsByRentalId(int rentId) throws BusinessException {
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
                throw new BusinessException(BusinessMessages.ERROR_CAR_STILL_RENTED_CANNOT_GO_MAINTENANCE);
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
        List<Rental> rentals = this.rentalDao.findAllByCar_CarId(createRentalRequest.getCarId());
        for(Rental rental:rentals){
            if (createRentalRequest.getRentDate().isBefore(rental.getRentDate())){
                if(createRentalRequest.getRentReturnDate().isAfter(rental.getRentReturnDate())){
                    throw new BusinessException(BusinessMessages.ERROR_CAR_ALREADY_RENTED);
                }
                if ( (createRentalRequest.getRentReturnDate().isBefore(rental.getRentReturnDate())) ||
                        (createRentalRequest.getRentReturnDate().isEqual(rental.getRentReturnDate()))){
                    if( (createRentalRequest.getRentReturnDate().isAfter(rental.getRentDate())) || (createRentalRequest.getRentReturnDate().isEqual(rental.getRentDate()))){
                        throw new BusinessException(BusinessMessages.ERROR_CAR_ALREADY_RENTED);
                    }
                }
            }
            if(createRentalRequest.getRentDate().isEqual(rental.getRentDate())){
                throw new BusinessException(BusinessMessages.ERROR_CAR_ALREADY_RENTED);
            }
            if( (createRentalRequest.getRentDate().isAfter(rental.getRentDate()) || createRentalRequest.getRentDate().isEqual(rental.getRentDate()))) {
                if ((createRentalRequest.getRentDate().isBefore(rental.getRentReturnDate())) || (createRentalRequest.getRentDate().isEqual(rental.getRentReturnDate()))){
                        throw new BusinessException(BusinessMessages.ERROR_CAR_ALREADY_RENTED);
                }
            }
        }
    }

    private void isCarCanRented(UpdateRentalRequest updateRentalRequest) throws BusinessException{
        List<Rental> rentals = this.rentalDao.getRentalByCar_CarId(updateRentalRequest.getCarId());
        for(Rental rental:rentals){
            if (updateRentalRequest.getRentDate().isBefore(rental.getRentDate())){
                if((updateRentalRequest.getRentReturnDate().isAfter(rental.getRentReturnDate())) && (rental.getRentId() != updateRentalRequest.getRentId())){
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

    @Override
    public void setAdditionForRental(Rental rental, CreateRentalRequest createRentalRequest) throws BusinessException {

        if( createRentalRequest.getAdditionId() == null || createRentalRequest.getAdditionId().isEmpty()){
            rental.setAdditionList(null);
        }
        else{
            List<Addition> tempAdditions = new ArrayList<>();

            for (Integer addId: createRentalRequest.getAdditionId()){
                this.additionService.isExistsByAdditionId(addId);

                Addition addition = this.additionService.getAdditionById(addId);
                tempAdditions.add(addition);
            }
            rental.setAdditionList(tempAdditions);
        }
    }


    private void setAdditionForRental(Rental rental, UpdateRentalRequest updateRentalRequest) throws BusinessException {

        if( updateRentalRequest.getAdditionId()==null || updateRentalRequest.getAdditionId().isEmpty()){
            rental.setAdditionList(null);
        }
        else{
            List<Addition> tempAdditions = new ArrayList<>();

            for (Integer addId: updateRentalRequest.getAdditionId()){
                this.additionService.isExistsByAdditionId(addId);
                Addition addition = this.additionService.getAdditionById(addId);
                tempAdditions.add(addition);
            }
            rental.setAdditionList(tempAdditions);
        }
    }
}
