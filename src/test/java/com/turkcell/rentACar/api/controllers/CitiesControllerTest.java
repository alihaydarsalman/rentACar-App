package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.concretes.CityManager;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.requests.create.CreateCityRequest;
import com.turkcell.rentACar.entities.sourceEntities.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hzyazilimci
 */

@ExtendWith(MockitoExtension.class)
class CitiesControllerTest {

    @Mock
    private CityManager mockService;

    @InjectMocks
    private CitiesController underTest;

    @BeforeEach
    void setUp() {

    }

    private CreateCityRequest generateCityRequestObject(){
        return CreateCityRequest.builder()
                .plateNo(34)
                .cityName("Istanbul")
                .build();
    }

    private City generateCityModel(){
        return City.builder()
                .cityId(34)
                .cityName("Istanbul")
                .build();
    }

    @Test
    @DisplayName("Valid istek atildiginda basarili sonuc donmesi testi")
    public void whenCreateCityCalledWithValidRequest_itShouldReturnSuccessResult(){

        CreateCityRequest request = generateCityRequestObject();
        Result result = underTest.add(request);

        Mockito.when(mockService.add(request)).thenReturn(result);


    }
}