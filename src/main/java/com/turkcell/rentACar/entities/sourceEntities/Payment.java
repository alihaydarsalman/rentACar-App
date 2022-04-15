package com.turkcell.rentACar.entities.sourceEntities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int paymentId;

    @Column(name = "payment_amount")
    private double paymentAmount;

    @CreationTimestamp
    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
