package com.turkcell.rentACar.dataAccess;

import com.turkcell.rentACar.entities.sourceEntities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarDao extends JpaRepository<Car,Integer> {

    boolean existsByCarId(int carId);
    List<Car> findAllByDailyPriceLessThanEqual(double dailyPrice);
    List<Car> findAllByDailyPriceGreaterThanEqual(double dailyPrice);
    boolean existsByBrand_BrandId(int branId);
    boolean existsByColor_ColorId(int colorId);
    List<Car> findAllByColor_ColorId(int colorId);
    List<Car> findAllByBrand_BrandId(int brandId);
    List<Car> findAllByModelYear(int modelYear);
}
