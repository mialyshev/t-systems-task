package com.javaschool.entity;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(of = {"id"})
@ToString(of = { "id", "size"})
@Entity
@Table(name = "sizes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "size")
    private float size;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL)
    private List<Product> products;
}
