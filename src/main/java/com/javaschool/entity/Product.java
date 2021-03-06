package com.javaschool.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "quantity", "price", "model"})
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private int price;

    @Column(name = "model")
    private String model;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "picture_url")
    private String url;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "season_id")
    private Season season;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToMany(mappedBy = "productList", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Order> orderList;
}

