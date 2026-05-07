package com.rinos.productservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private String status;
    private Date created_at;
    private Date updated_at;
}
