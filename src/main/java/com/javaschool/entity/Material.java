package com.javaschool.entity;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "materialName"})
@Entity
@Table(name = "materials")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "material_name")
    private String materialName;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private List<Product> products;

}