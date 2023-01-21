package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.*;

import com.turkcell.rentACar.dataAccess.RentalDao;
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
class RentalManagerTest {

    /**
     * Mock Services/Repositories
     * */
    @Mock
    private RentalDao rentalDao;
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

    @InjectMocks
    private RentalManager underTest;

    @BeforeEach
    void setUp() {
    }




}