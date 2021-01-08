package com.javaschool.entity;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sizes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "size")
    private Float size;

//    @LazyCollection(LazyCollectionOption.FALSE)
//    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
//    private List<Product> products;
}
