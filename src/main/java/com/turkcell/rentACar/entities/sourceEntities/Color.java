package com.turkcell.rentACar.entities.sourceEntities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "colors")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private int colorId;

    @Column(name = "color_name")
    private String colorName;

    @OneToMany(mappedBy = "color")
    private List<Car> cars;
}
