package com.rinos.productservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)  // Many products belong to one category
    @JoinColumn(name = "category_id")
    private Category category;  // Each product stores ONE category object.

    private String status;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,  // If product is saved, its images can also be saved. If product is deleted, images are also deleted.
            orphanRemoval = true    // If an image is removed from product image list, that image row will also be removed from database.
    )
    private List<ProductImage> images = new ArrayList<>();

    @CreationTimestamp  // Auto-sets on create
    @Column(updatable = false)  // Never update after creation
    private LocalDateTime createdAt;

    @UpdateTimestamp  // Auto-sets on create & update
    private LocalDateTime updatedAt;
}
