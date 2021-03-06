package com.javaschool.entity;

import lombok.*;

import javax.persistence.*;


@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "country", "city", "postalCode", "street", "houseNumber", "apartamentNumber"})
@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "apartament_number")
    private String apartamentNumber;

    @Column(name = "is_saved")
    private boolean isSaved;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
//
//    @LazyCollection(LazyCollectionOption.FALSE)
//    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
//    private List<Order> orders;
}


