package com.turkcell.rentACar.dataAccess;

import com.turkcell.rentACar.entities.sourceEntities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {

}
