package com.turkcell.rentACar.entities.sourceEntities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "car_maintenances")
public class CarMaintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintenance_id")
    private int maintenanceId;

    @Column(name = "maintenance_desc")
    private String maintenanceDesc;

    @Column(name = "maintenance_return_date")
    private LocalDate maintenanceReturnDate;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}
