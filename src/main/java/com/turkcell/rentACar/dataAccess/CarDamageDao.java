package com.turkcell.rentACar.dataAccess;

import com.turkcell.rentACar.entities.sourceEntities.CarDamage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarDamageDao extends JpaRepository<CarDamage, Integer> {

    boolean existsByCar_CarId(int carId);

    List<CarDamage> findAllByCar_CarId(int carId);

    void deleteCarDamageByCar_CarId(int carId);
}
