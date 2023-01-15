package com.turkcell.rentACar.dataAccess;

import com.turkcell.rentACar.entities.sourceEntities.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorDao extends JpaRepository<Color,Integer> {
    boolean existsByColorName(String colorName);
    boolean existsByColorId(int colorId);
    Color findByColorId(int id);
}