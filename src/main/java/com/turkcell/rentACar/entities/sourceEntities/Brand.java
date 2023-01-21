package com.turkcell.rentACar.entities.sourceEntities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "brands")
@Builder
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private int brandId;

    @Column(name = "brand_name",unique = true)
    private String brandName;

    @OneToMany(mappedBy = "brand")
    private List<Car> cars;
}
