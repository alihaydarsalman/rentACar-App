package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.CityDao;
import com.turkcell.rentACar.entities.converters.CityConverter;
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
import java.util.ArrayList;
import java.util.List;
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

    @Mock
    private CityConverter mockConverter;

    @InjectMocks
    private CityManager cityManager;

    @BeforeEach
    void setUp(){

    }

    @Test
    @DisplayName("add metodu icin gecerli istek atilmasi durumu")
    public void whenCreateCityCalledWithValidRequest_itShouldReturnSuccessResult(){

        CreateCityRequest requestDto = generateCreateCityDto();

        City city = City.builder()
                .cityId(requestDto.getPlateNo())
                .cityName(requestDto.getCityName())
                .build();

        GetCityDto cityDto = GetCityDto.builder()
                .cityId(city.getCityId())
                .cityName(city.getCityName())
                .build();

        SuccessDataResult expectedResult = new SuccessDataResult(cityDto);

        when(mockCityDao.existsByCityName(requestDto.getCityName().toLowerCase(Locale.ROOT))).thenReturn(false);
        when(mockCityDao.save(city)).thenReturn(city);
        when(mockConverter.convertCityToDto(city)).thenReturn(cityDto);

        DataResult actualResult = cityManager.add(requestDto);

        assertAll(
                () -> assertTrue(actualResult.isSuccess()),
                () -> assertEquals(actualResult,expectedResult),
                () -> assertEquals(expectedResult.getData(),actualResult.getData())
        );

        verify(mockCityDao).existsByCityName(requestDto.getCityName().toLowerCase(Locale.ROOT));
        verify(mockCityDao).save(city);
        verify(mockConverter).convertCityToDto(city);
    }

    @Test
    @DisplayName("add metoduna var olan sehir icin istek atilmasi durumu")
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

        SuccessDataResult expectedResult = new SuccessDataResult(generateGetCityDto(city));

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
        int cityId = 34;
        String cityName = "Ankara";

        City currCity = City.builder().cityId(cityId).cityName(cityName).build(); //34 - Ankara
        UpdateCityRequest cityRequest = generateUpdateCityDto(); //34 - Istanbul request
        City updatedCity = generateCityModelForUpdateRequests(cityRequest); //34 - Istanbul newCity
        GetCityDto cityDto = GetCityDto.builder().cityId(updatedCity.getCityId()).cityName(updatedCity.getCityName()).build(); //34 - Istanbul GetCityDto

        when(mockCityDao.existsById(cityRequest.getPlateNo())).thenReturn(true);
        when(mockCityDao.save(updatedCity)).thenReturn(updatedCity);
        when(mockConverter.convertCityToDto(updatedCity)).thenReturn(cityDto);

        SuccessDataResult expectedResult = new SuccessDataResult(cityDto);

        DataResult actualResult = cityManager.update(cityRequest);

        assertAll(
                () -> assertNotNull(actualResult),
                () -> assertTrue(actualResult.isSuccess()),
                () -> assertEquals(expectedResult,actualResult),
                () -> assertNotEquals(actualResult.getData(), currCity),
                () -> assertEquals(expectedResult.getData(),actualResult.getData())
        );

        verify(mockCityDao).existsById(cityRequest.getPlateNo());
        verify(mockCityDao).save(updatedCity);
        verify(mockConverter).convertCityToDto(updatedCity);
    }

    @Test
    @DisplayName("update ile var olmayan bir id icin guncelleme istegi atilmasi durumu")
    public void whenUpdateRequestSentWithIdThatDoesNotExist_itShouldThrowBusinessException(){

        UpdateCityRequest request = generateUpdateCityDto(); //34 - Istanbul

        when(mockCityDao.existsById(request.getPlateNo())).thenReturn(false);

        assertThrows(BusinessException.class,() -> cityManager.update(request));

        verify(mockCityDao).existsById(request.getPlateNo());
        verifyNoMoreInteractions(mockCityDao);
        verifyNoInteractions(mockConverter);
    }

    @Test
    @DisplayName("basarili sekilde kayit silme durumu")
    public void whenDeleteCalledForAnExistingRecord_itShouldReturnSuccessResult(){

        int id = 34;

        when(mockCityDao.existsById(id)).thenReturn(true);
        doNothing().when(mockCityDao).deleteById(id);

        SuccessResult expectedResult = new SuccessResult();
        Result actualResult = cityManager.delete(id);

        assertAll(
                () -> assertEquals(actualResult,expectedResult)
        );

        verify(mockCityDao).existsById(id);
        verifyNoInteractions(mockConverter);
        verifyNoMoreInteractions(mockCityDao);
    }

    @Test
    @DisplayName("var olmayan bir kaydin silinmek istenmesi durumu")
    public void whenDeleteCalledForDoesNotExistRecord_itShouldReturnBusinessException(){

        int id = 79;

        when(mockCityDao.existsById(id)).thenReturn(false);

        assertThrows(BusinessException.class,() -> cityManager.delete(id));

        verify(mockCityDao).existsById(id);
        verifyNoInteractions(mockConverter);
        verifyNoMoreInteractions(mockCityDao);
    }

    @Test
    public void whenGetAllCalled_itShouldReturnListOfGetCityDto(){

        City city1 = generateCityModel2();
        City city2 = generateCityModel3();
        GetCityDto getCityDto1 = generateGetCityDto(city1);
        GetCityDto getCityDto2 = generateGetCityDto(city2);

        List<City> cityList = new ArrayList<>();
        cityList.add(city1);
        cityList.add(city2);

        List<GetCityDto> getCityDtoList = new ArrayList<>();
        getCityDtoList.add(getCityDto2);
        getCityDtoList.add(getCityDto1);

        when(mockCityDao.findAll()).thenReturn(cityList);
        when(mockConverter.convertCityToDto(cityList)).thenReturn(getCityDtoList);

        SuccessDataResult expectedResult = new SuccessDataResult(getCityDtoList);

        DataResult actualResult = cityManager.getAll();

        assertAll(
                () -> assertNotNull(actualResult),
                () -> assertEquals(actualResult,expectedResult),
                () -> assertEquals(actualResult.getData(),expectedResult.getData()),
                () -> assertEquals(getCityDtoList,actualResult.getData())
        );

        verify(mockCityDao).findAll();
        verify(mockConverter).convertCityToDto(cityList);
        verifyNoMoreInteractions(mockCityDao);
        verifyNoMoreInteractions(mockConverter);
    }

    private City generateCityModel(){

        return City.builder()
                .cityId(34)
                .cityName("Ankara")
                .build();
    }

    private City generateCityModel2(){

        return City.builder()
                .cityId(35)
                .cityName("Izmir")
                .build();
    }

    private City generateCityModel3(){

        return City.builder()
                .cityId(38)
                .cityName("Kayseri")
                .build();
    }

    private GetCityDto generateGetCityDto(City city){

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