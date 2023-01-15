package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.CityDao;
import com.turkcell.rentACar.entities.dtos.get.GetCityDto;
import com.turkcell.rentACar.entities.requests.create.CreateCityRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateCityRequest;
import com.turkcell.rentACar.entities.sourceEntities.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * @author hzyazilimci
 */

@ExtendWith(MockitoExtension.class)
class CityManagerTest {

    @Mock
    private ModelMapperService mockMapper;

    @Mock
    private CityDao mockCityDao;

    @InjectMocks
    private CityManager cityManager;

    @BeforeEach
    void setUp(){

    }

    @Test
    @DisplayName("gecerli istek atilmasi durumu")
    public void whenCreateCityCalledWithValidRequest_itShouldReturnSuccessResult(){

        CreateCityRequest createCityRequest = generateCreateCityDto();
        City city = generateCityModelForCreateRequests(createCityRequest);
        SuccessResult expectedResult = new SuccessResult();

        when(mockCityDao.existsByCityName(createCityRequest.getCityName().toLowerCase(Locale.ROOT))).thenReturn(false);
        when(mockCityDao.save(city)).thenReturn(city);

        Result actualResult = cityManager.add(createCityRequest);

        assertAll(
                () -> assertEquals(actualResult, expectedResult),
                () -> assertTrue(actualResult.isSuccess()),
                () -> assertEquals(city.getCityName(), createCityRequest.getCityName())
        );

        verify(mockCityDao).existsByCityName(createCityRequest.getCityName().toLowerCase(Locale.ROOT));
        verify(mockCityDao).save(city);
        verifyNoInteractions(mockMapper);
    }

    @Test
    @DisplayName("ayni isimle sehir gonderilmesi durumu")
    public void whenCreateCityCalledWithExistsCityName_itShouldThrowBusinessException(){

        CreateCityRequest requestDto = generateCreateCityDto();

        when(mockCityDao.existsByCityName(requestDto.getCityName().toLowerCase(Locale.ROOT))).thenReturn(true);

        Executable executable = () -> cityManager.add(requestDto);

        assertThrows(BusinessException.class,executable);

        verify(mockCityDao).existsByCityName(requestDto.getCityName().toLowerCase(Locale.ROOT));
        verifyNoInteractions(mockMapper);
    }

    @Test
    @DisplayName("getById ile basarili sekilde sehrin getirilmesi durumu")
    public void whenGetByIdCalledExistsId_itShouldReturnDataResultOfCity(){

        int id = 34;
        City city = generateCityModel();

        SuccessDataResult expectedResult = new SuccessDataResult(generateGetCityDtoForGetById(city));

        when(mockCityDao.existsById(id)).thenReturn(true);
        when(mockCityDao.getById(id)).thenReturn(city);

        DataResult actualResult = cityManager.getById(id);

        assertAll(
                () -> assertNotNull(actualResult),
                () -> assertTrue(actualResult.isSuccess()),
                () -> assertEquals(expectedResult,actualResult),
                () -> assertEquals(expectedResult.getData(), actualResult.getData())
        );

        verify(mockCityDao).existsById(id);
        verify(mockCityDao).getById(id);
        verifyNoInteractions(mockMapper);
    }

    @Test
    @DisplayName("var olmayan bir id ile sehir aramasi yapilmasi durumu")
    public void whenGetByIdCalledNotExistsId_itShouldThrowBusinessException(){

        int id = 6;

        when(mockCityDao.existsById(id)).thenReturn(false);

        Executable executable = () -> cityManager.getById(id);

        assertThrows(BusinessException.class,executable);

        verify(mockCityDao).existsById(id);
        verifyNoInteractions(mockMapper);
    }

    @Test
    @DisplayName("update ile basarili sekilde sehir guncelleme durumu")
    public void whenUpdateMethodExecuteSuccessfully_itShouldReturnSuccessResult(){

        int id = 34;
        String cityName = "Ankara";

        City currCity = City.builder()   //eski hali - 34,ANKARA
                .cityId(id)
                .cityName(cityName)
                .build();

        UpdateCityRequest requestObject = generateUpdateCityDto(); //istenen yeni alanlar bunla gelecek - 34,ISTANBUL

        City newCity = City.builder()
                        .cityId(requestObject.getPlateNo())
                        .cityName(requestObject.getCityName())
                        .build();

        SuccessDataResult expectedResult = new SuccessDataResult(GetCityDto.builder()
                .cityId(newCity.getCityId())
                .cityName(newCity.getCityName())
                .build());

        when(mockCityDao.existsById(requestObject.getPlateNo())).thenReturn(true);
        when(mockCityDao.save(newCity)).thenReturn(newCity);

        DataResult actualResult = cityManager.update(requestObject); //db' ye save edilen son halini dondu

        assertAll(
                () -> assertNotNull(actualResult),
                () -> assertTrue(actualResult.isSuccess()),
                () -> assertEquals(actualResult,expectedResult),
                () -> assertEquals(expectedResult.getData(),actualResult.getData()),
                () -> assertNotEquals(currCity,actualResult.getData())
        );

        verify(mockCityDao).existsById(requestObject.getPlateNo());
        verify(mockCityDao).save(newCity);
        verifyNoInteractions(mockMapper);
    }

    @Test
    @DisplayName("update ile var olmayan bir id")

    private City generateCityModel(){

        return City.builder()
                .cityId(34)
                .cityName("Ankara")
                .build();
    }

    private GetCityDto generateGetCityDtoForGetById(City city){

        return GetCityDto.builder()
                .cityId(city.getCityId())
                .cityName(city.getCityName())
                .build();
    }

    private CreateCityRequest generateCreateCityDto(){

        return CreateCityRequest.builder()
                .plateNo(34)
                .cityName("Istanbul")
                .build();
    }

    private UpdateCityRequest generateUpdateCityDto(){

        return UpdateCityRequest.builder()
                .plateNo(34)
                .cityName("Istanbul")
                .build();
    }

    private City generateCityModelForUpdateRequests(UpdateCityRequest updateCityRequest){

        return City.builder()
                .cityId(updateCityRequest.getPlateNo())
                .cityName(updateCityRequest.getCityName())
                .build();
    }

    private City generateCityModelForCreateRequests(CreateCityRequest createCityRequest){

        return City.builder()
                .cityId(createCityRequest.getPlateNo())
                .cityName(createCityRequest.getCityName())
                .build();
    }
}