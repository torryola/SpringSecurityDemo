package net.torrydev.stockmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Represents the products table in Database. This is similar to Entity using Hibernate or JPA
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Products {
    private String productCode, productName, productLine, productScale, productVendor, productDescription;
    private int quantityInStock;
    private double buyPrice, MSRP;
}
