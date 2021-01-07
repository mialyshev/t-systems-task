package com.javaschool.entity;

import lombok.*;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@NamedQuery(
        name = "findUserWithEmail",
        query = "SELECT u from User u where u.email = :userEmail"
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "user_role",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id")
            }
    )
    private Set<Role> roleSet;

//    @LazyCollection(LazyCollectionOption.FALSE)
//    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
//    private List<Card> cards;
//
//    @LazyCollection(LazyCollectionOption.FALSE)
//    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
//    private List<Address> addresses;
//
//    @LazyCollection(LazyCollectionOption.FALSE)
//    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
//    private List<Order> orders;
}