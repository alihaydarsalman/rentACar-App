package com.turkcell.rentACar.dataAccess;

import com.turkcell.rentACar.entities.sourceEntities.CarMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarMaintenanceDao extends JpaRepository<CarMaintenance, Integer> {

    List<CarMaintenance> getCarMaintenanceByCar_CarId(int carId);

    List<CarMaintenance> findCarMaintenanceByCar_CarId(int carId);

    boolean existsByCar_CarId(int carId);

}
