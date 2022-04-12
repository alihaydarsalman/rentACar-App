package com.turkcell.rentACar.dataAccess;

import com.turkcell.rentACar.entities.sourceEntities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer> {

    List<Invoice> findAllByCreationDateBetween(LocalDate firstDate, LocalDate secondDate);

    List<Invoice> findAllByCustomer_UserId(int id);

    boolean existsByCustomer_UserId(int id);
}
