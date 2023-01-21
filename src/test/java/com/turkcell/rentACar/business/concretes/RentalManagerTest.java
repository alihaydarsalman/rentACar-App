package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.*;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.dataAccess.RentalDao;
import com.turkcell.rentACar.entities.converters.RentalConverter;
import com.turkcell.rentACar.entities.dtos.get.GetRentalDto;
import com.turkcell.rentACar.entities.requests.create.CreateRentalRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateRentalRequest;
import com.turkcell.rentACar.entities.sourceEntities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.Collections;

/**
 * @author hzyazilimci
 */

@ExtendWith(MockitoExtension.class)
class RentalManagerTest {

    /**
     * Mock Services/Repositories
     * */
    @Mock
    private RentalDao mockDao;
    @Mock
    private CarService carService;
    @Mock
    private CarMaintenanceService carMaintenanceService;
    @Mock
    private AdditionService additionService;
    @Mock
    private CityService cityService;
    @Mock
    private IndividualCustomerService individualCustomerService;
    @Mock
    private CorporateCustomerService corporateCustomerService;
    @Mock
    private RentalConverter mockConverter;

    static int ADDITION_ID = 1;
    static int USER_ID = 5;

    @InjectMocks
    private RentalManager underTest;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void whenGetAllCalled_itShouldReturnListOfGetRentalDto(){

        List<Rental> rentals = Collections.singletonList(generateRentalModel());
        List<GetRentalDto> rentalDtos = Collections.singletonList(generateGetRentalDto(rentals.get(0)));

        when(mockDao.findAll()).thenReturn(rentals);
        when(mockConverter.convertRentalToDto(rentals)).thenReturn(rentalDtos);

        SuccessDataResult expectedResult = new SuccessDataResult(rentalDtos);

        DataResult actualResult = underTest.getAll();

        assertAll(
                () -> assertNotNull(actualResult),
                () -> assertTrue(actualResult.isSuccess()),
                () -> assertEquals(actualResult,expectedResult),
                () -> assertEquals(actualResult.getData(),rentalDtos)
        );

        verify(mockDao).findAll();
        verify(mockConverter).convertRentalToDto(rentals);
        verifyNoMoreInteractions(mockDao);
    }

    @Test
    @DisplayName("dogru bir kiralama kaydi istegi atilmasi durumu(individual customer)")
    public void whenAddRentalForIndCustomerWithValidRequest_itShouldReturnRental(){

        IndividualCustomer customer = generateIndividualCustomer();
        City fromCity = generateCityList().get(0);
        City toCity = generateCityList().get(1);
        Car car = generateCarModel();
        List<Addition> additionList = generateAdditionList();

        CreateRentalRequest request = CreateRentalRequest.builder()
                .rentDate(LocalDate.now())
                .rentReturnDate(LocalDate.now().plusDays(3))
                .carId(car.getCarId())
                .fromCityId(generateCityList().get(0).getCityId())
                .toCityId(generateCityList().get(1).getCityId())
                .userId(USER_ID)
                .additionId(Collections.singletonList(ADDITION_ID))
                .build();

        Rental rental = Rental.builder()
                .rentDate(request.getRentDate())
                .rentReturnDate(request.getRentReturnDate())
                .car(car)
                .customer(customer)
                .fromCity(fromCity)
                .toCity(toCity)
                .additionList(additionList)
                .build();

        /**
         * only void methods can doNothing()
         * */
        doNothing().when(carService).isExistsByCarId(request.getCarId());
        doNothing().when(cityService).isExistsByCityId(request.getFromCityId());
        doNothing().when(cityService).isExistsByCityId(request.getToCityId());
        doNothing().when(carMaintenanceService).isCarUnderMaintenanceForRental(request.getCarId(),request.getRentDate());
        doNothing().when(individualCustomerService).isIndividualCustomerExistsById(request.getUserId());
        when(carService.getCarByCarId(request.getCarId())).thenReturn(generateCarModel());
        when(individualCustomerService.getCustomerById(USER_ID)).thenReturn(customer);
        when(cityService.getCityById(request.getFromCityId())).thenReturn(fromCity);
        when(cityService.getCityById(request.getToCityId())).thenReturn(toCity);
        when(additionService.getAdditionById(ADDITION_ID)).thenReturn(additionList.get(0));
        doNothing().when(additionService).isExistsByAdditionId(ADDITION_ID);
        when(mockDao.save(rental)).thenReturn(rental);

        Rental actualRental = underTest.addRentalForIndividualCustomer(request);

        assertAll(
                () -> assertNotNull(rental),
                () -> assertEquals(rental, actualRental),
                () -> assertEquals(actualRental.getCar().getCarId(),request.getCarId())
        );

        verify(carService).getCarByCarId(request.getCarId());
        verify(individualCustomerService).getCustomerById(USER_ID);
        verify(cityService).getCityById(request.getFromCityId());
        verify(cityService).getCityById(request.getToCityId());
        verify(additionService).getAdditionById(ADDITION_ID);
        verify(mockDao).save(rental);
    }

    @Test
    @DisplayName("dogru bir kiralama kaydi istegi atilmasi durumu(corporate customer)")
    public void whenAddRentalForCorpCustomerWithValidRequest_itShouldReturnRental(){

        CorporateCustomer customer = generateCorporateCustomer();
        City fromCity = generateCityList().get(0);
        City toCity = generateCityList().get(1);
        Car car = generateCarModel();
        List<Addition> additionList = generateAdditionList();

        CreateRentalRequest request = CreateRentalRequest.builder()
                .rentDate(LocalDate.now())
                .rentReturnDate(LocalDate.now().plusDays(3))
                .carId(car.getCarId())
                .fromCityId(generateCityList().get(0).getCityId())
                .toCityId(generateCityList().get(1).getCityId())
                .userId(USER_ID)
                .additionId(Collections.singletonList(ADDITION_ID))
                .build();

        Rental rental = Rental.builder()
                .rentDate(request.getRentDate())
                .rentReturnDate(request.getRentReturnDate())
                .car(car)
                .customer(customer)
                .fromCity(fromCity)
                .toCity(toCity)
                .additionList(additionList)
                .build();

        doNothing().when(carService).isExistsByCarId(request.getCarId());
        doNothing().when(cityService).isExistsByCityId(request.getFromCityId());
        doNothing().when(cityService).isExistsByCityId(request.getToCityId());
        doNothing().when(carMaintenanceService).isCarUnderMaintenanceForRental(request.getCarId(),request.getRentDate());
        doNothing().when(corporateCustomerService).isCorporateCustomerExistsById(request.getUserId());
        when(carService.getCarByCarId(request.getCarId())).thenReturn(generateCarModel());
        when(corporateCustomerService.getCustomerById(USER_ID)).thenReturn(customer);
        when(cityService.getCityById(request.getFromCityId())).thenReturn(fromCity);
        when(cityService.getCityById(request.getToCityId())).thenReturn(toCity);
        when(additionService.getAdditionById(ADDITION_ID)).thenReturn(additionList.get(0));
        doNothing().when(additionService).isExistsByAdditionId(ADDITION_ID);
        when(mockDao.save(rental)).thenReturn(rental);

        Rental actualRental = underTest.addRentalForCorporateCustomer(request);

        assertAll(
                () -> assertNotNull(rental),
                () -> assertEquals(rental, actualRental),
                () -> assertEquals(actualRental.getCar().getCarId(),request.getCarId())
        );

        verify(carService).getCarByCarId(request.getCarId());
        verify(corporateCustomerService).getCustomerById(USER_ID);
        verify(cityService).getCityById(request.getFromCityId());
        verify(cityService).getCityById(request.getToCityId());
        verify(additionService).getAdditionById(ADDITION_ID);
        verify(mockDao).save(rental);
    }


    @Test
    @DisplayName("olmayan bir kiralamanin guncellenmek istenmesi(ind cust)")
    public void updateRentalForIndividualCustomerCalledForDoesNotExistRentId_itShouldThrowBusinessException(){

        Car car = generateCarModel();

        UpdateRentalRequest request = UpdateRentalRequest.builder()
                .rentDate(LocalDate.now())
                .rentReturnDate(LocalDate.now().plusDays(3))
                .carId(car.getCarId())
                .fromCityId(generateCityList().get(0).getCityId())
                .toCityId(generateCityList().get(1).getCityId())
                .userId(USER_ID)
                .additionId(Collections.singletonList(ADDITION_ID))
                .build();

        when(mockDao.existsById(request.getRentId())).thenReturn(false);

        assertThrows(BusinessException.class, () -> underTest.updateRentalForIndividualCustomer(request));

        verify(mockDao).existsById(request.getRentId());
        verifyNoMoreInteractions(mockDao);
        verifyNoInteractions(carService);
        verifyNoInteractions(carMaintenanceService);
        verifyNoInteractions(individualCustomerService);
    }

    @Test
    @DisplayName("olmayan bir kiralamanin guncellenmek istenmesi(corp cus)")
    public void updateRentalForCorporateCustomerCalledForDoesNotExistRentId_itShouldThrowBusinessException(){

        Car car = generateCarModel();

        UpdateRentalRequest request = UpdateRentalRequest.builder()
                .rentDate(LocalDate.now())
                .rentReturnDate(LocalDate.now().plusDays(3))
                .carId(car.getCarId())
                .fromCityId(generateCityList().get(0).getCityId())
                .toCityId(generateCityList().get(1).getCityId())
                .userId(USER_ID)
                .additionId(Collections.singletonList(ADDITION_ID))
                .build();

        when(mockDao.existsById(request.getRentId())).thenReturn(false);

        assertThrows(BusinessException.class, () -> underTest.updateRentalForCorporateCustomer(request));

        verify(mockDao).existsById(request.getRentId());
        verifyNoMoreInteractions(mockDao);
        verifyNoInteractions(carService);
        verifyNoInteractions(carMaintenanceService);
        verifyNoInteractions(corporateCustomerService);
    }

    private IndividualCustomer generateIndividualCustomer(){
        return new IndividualCustomer("12312312312","Alihaydar","Salman");
    }

    private CorporateCustomer generateCorporateCustomer(){
        return new CorporateCustomer("1110002221","hzyazilimci co");
    }

    private List<City> generateCityList(){
        City city1 = City.builder()
                .cityId(34)
                .cityName("Istanbul")
                .build();

        City city2 = City.builder()
                .cityId(6)
                .cityName("Ankara")
                .build();

        List<City> cities = new ArrayList<>();
        cities.add(city1);
        cities.add(city2);

        return cities;
    }

    private Color generateColorModel(){
        return Color.builder()
                .colorId(12)
                .colorName("Black")
                .build();
    }

    private Brand generateBrandModel(){
        return Brand.builder()
                .brandId(7)
                .brandName("Mercedes")
                .build();
    }

    private Car generateCarModel(){
        return Car.builder()
                .carId(15)
                .dailyPrice(1100)
                .modelYear(2016)
                .description("exclusive car")
                .currentKilometer(7000)
                .brand(generateBrandModel())
                .color(generateColorModel())
                .build();
    }

    private List<Addition> generateAdditionList(){
        Addition addition = new Addition(1, "Scooter",150);

        return Collections.singletonList(addition);

    }

    private Rental generateRentalModel(){
        return Rental.builder()
                .rentId(1)
                .rentDate(LocalDate.now())
                .rentReturnDate(LocalDate.now().plusDays(7))
                .rentKilometer(generateCarModel().getCurrentKilometer())
                .rentReturnKilometer(9500)
                .car(generateCarModel())
                .fromCity(generateCityList().get(0))
                .toCity(generateCityList().get(1))
                .build();
    }

    public GetRentalDto generateGetRentalDto(Rental rental){
        return GetRentalDto.builder()
                .rentId(rental.getRentId())
                .rentDate(rental.getRentDate())
                .rentReturnDate(rental.getRentReturnDate())
                .brandName(rental.getCar().getBrand().getBrandName())
                .description(rental.getCar().getDescription())
                .rentKilometer(rental.getRentKilometer())
                .rentReturnKilometer(rental.getRentReturnKilometer())
                .fromCityName(rental.getFromCity().getCityName())
                .toCityName(rental.getToCity().getCityName())
                .build();
    }
}