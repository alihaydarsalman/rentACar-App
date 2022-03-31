package com.turkcell.rentACar.dataAccess;

import com.turkcell.rentACar.entities.sourceEntities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalDao extends JpaRepository<Rental, Integer> {

    List<Rental> findAllByCar_CarId(int carId);

    boolean existsByCar_CarId(int carId);

    List<Rental> getRentalByCar_CarId(int carId);

}
