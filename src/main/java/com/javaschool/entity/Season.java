package com.javaschool.entity;


import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;


@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "seasons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "season_name")
    private String seasonName;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    private List<Product> products;

}
