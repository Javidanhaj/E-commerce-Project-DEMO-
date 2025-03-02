// Java
package com.javidanhaj.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;
    private Date releaseDate;
    @Getter
    @Setter
    private boolean productAvailable = false; // Default value
    private int stockQuantity;
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;

}