package com.turkcell.rentACar.dataAccess;

import com.turkcell.rentACar.entities.sourceEntities.CorporateCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer,Integer> {

    boolean existsCorporateCustomerByTaxNumber(String taxNumber);
}
