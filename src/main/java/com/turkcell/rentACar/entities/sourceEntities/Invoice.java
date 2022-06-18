package com.turkcell.rentACar.entities.sourceEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "Lazy"})
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int invoiceId;

    @Column(name = "invoice_no", unique = true)
    private String invoiceNo;

    @CreationTimestamp
    @Column(name = "invoice_creation_date")
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "rent_id")
    private Rental rental;

    @Column(name = "rent_date")
    private LocalDate rentDate;

    @Column(name = "rent_return_date")
    private LocalDate rentReturnDate;

    @Column(name = "total_rent_days")
    private int totalRentalDays;

    @Column(name = "total_price")
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer customer;

    @OneToOne(mappedBy = "invoice")
    private Payment payment;

    @Transient
    public static final double diffCityPrice = 750;
}
