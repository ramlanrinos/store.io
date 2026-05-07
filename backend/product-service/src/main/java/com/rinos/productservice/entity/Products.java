package com.rinos.productservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private double price;
    private String brand;
    private int category_id;
    private String Status;
    private Date created_at;
    private Date updated_at;
}
