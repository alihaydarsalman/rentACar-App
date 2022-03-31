package com.turkcell.rentACar.dataAccess;

import com.turkcell.rentACar.entities.sourceEntities.IndividualCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer, Integer> {

    boolean existsIndividualCustomerByNationalId(String nationalId);
}
