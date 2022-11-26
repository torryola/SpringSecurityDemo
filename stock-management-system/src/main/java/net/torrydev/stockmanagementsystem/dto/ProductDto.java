package net.torrydev.stockmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @Data @NoArgsConstructor @AllArgsConstructor
public class ProductDto {
    private String productCode, productName, productLine, productScale, productVendor, productDescription;
    private int quantityInStock;
    private double buyPrice, MSRP;
}
