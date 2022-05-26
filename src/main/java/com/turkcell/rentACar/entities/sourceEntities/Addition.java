package com.turkcell.rentACar.entities.sourceEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "additions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "Lazy"})
public class Addition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addition_id")
    private int additionId;

    @Column(name = "addition_name")
    private String additionName;

    @Column(name = "addition_daily_price")
    private double additionDailyPrice;
}
