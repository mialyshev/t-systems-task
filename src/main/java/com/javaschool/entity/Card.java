package com.javaschool.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "number", "validatyDate", "owner", "code"})
@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "card_number")
    private String number;

    @Column(name = "validaty_date")
    private LocalDate validatyDate;

    @Column(name = "card_owner")
    private String owner;

    @Column(name = "code")
    private String code;

    @Column(name = "last_numbers")
    private String lastNumbers;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}