package com.javaschool.entity;

import com.javaschool.entity.enums.OrderStatus;
import com.javaschool.entity.enums.PaymentStatus;
import com.javaschool.entity.enums.PaymentType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "date")
    private LocalDate dateOfPurchase;

        @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "product_order",
            joinColumns = {
                    @JoinColumn(name = "order_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "product_id", referencedColumnName = "id")
            }
    )
    private List<Product> productList;
}