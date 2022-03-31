package com.turkcell.rentACar.entities.sourceEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_id")
    private int rentId;

    @Column(name = "rent_date")
    private LocalDate rentDate;

    @Column(name = "rent_return_date")
    private LocalDate rentReturnDate;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToMany
    @JoinTable(name = "rentals_additions",
            joinColumns = {@JoinColumn(name = "rent_id")},
            inverseJoinColumns = {@JoinColumn(name = "addition_id")})
    private List<Addition> additions;

    @OneToOne
    @JoinColumn(name = "from_city_id")
    private City fromCity;

    @OneToOne
    @JoinColumn(name = "to_city_id")
    private City toCity;
}
