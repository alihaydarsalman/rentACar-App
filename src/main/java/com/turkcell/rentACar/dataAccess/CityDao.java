package com.turkcell.rentACar.dataAccess;

import com.turkcell.rentACar.entities.sourceEntities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDao extends JpaRepository<City, Integer> {
}
