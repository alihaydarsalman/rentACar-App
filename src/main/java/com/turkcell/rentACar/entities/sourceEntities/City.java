package com.turkcell.rentACar.entities.sourceEntities;


import lombok.*;

import javax.persistence.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cities")
@Builder
@EqualsAndHashCode
public class City {

    @Id
    @Column(name = "city_id")
    private int cityId;

    @Column(name = "city_name")
    private String cityName;

    @OneToMany(mappedBy = "fromCity")
    private List<Rental> fromCity;

    @OneToMany(mappedBy = "toCity")
    private List<Rental> toCity;
}
