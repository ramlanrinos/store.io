package com.rinos.productservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private double price;
    private String brand;
    private int categoryId;
    private String status;

    @CreationTimestamp  // Auto-sets on create
    @Column(updatable = false)  // Never update after creation
    private Date createdAt;

    @UpdateTimestamp  // Auto-sets on create & update
    private Date updatedAt;
}
