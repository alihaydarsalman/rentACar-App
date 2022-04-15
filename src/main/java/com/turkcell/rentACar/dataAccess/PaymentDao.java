package com.turkcell.rentACar.dataAccess;

import com.turkcell.rentACar.entities.sourceEntities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDao extends JpaRepository<Payment,Integer> {

    List<Payment> findAllByInvoice_Customer_UserId(int userId);

    List<Payment> findAllByInvoice_Rental_RentId(int rentId);

    boolean existsPaymentByInvoice_Customer_UserId(int invoiceId);

    boolean existsPaymentByInvoice_Rental_RentId(int rentId);
}
