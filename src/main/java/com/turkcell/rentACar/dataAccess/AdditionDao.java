package com.turkcell.rentACar.dataAccess;

import com.turkcell.rentACar.entities.sourceEntities.Addition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionDao extends JpaRepository<Addition,Integer> {

    boolean existsByAdditionId(int addId);
    boolean existsByAdditionName(String addName);
    Addition findAdditionByAdditionId(int addId);
}
