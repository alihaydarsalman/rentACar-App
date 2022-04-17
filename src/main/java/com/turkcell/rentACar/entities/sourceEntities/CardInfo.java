package com.turkcell.rentACar.entities.sourceEntities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "card_infos")
public class CardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_info_id")
    private int cardInfoId;

    @Column(name = "card_holder")
    private String cardHolder;

    @Column(name = "card_number", length = 16, unique = true)
    private String cardNumber;

    @Column(name = "cvv")
    private int cvv;

    @Column(name = "expiration_month")
    private int expirationMonth;

    @Column(name = "expiration_year")
    private int expirationYear;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer customer;
}
