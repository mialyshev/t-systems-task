package com.javaschool.entity;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "colorName"})
@Entity
@Table(name = "colors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "color_name")
    private String colorName;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
    private List<Product> products;

}
