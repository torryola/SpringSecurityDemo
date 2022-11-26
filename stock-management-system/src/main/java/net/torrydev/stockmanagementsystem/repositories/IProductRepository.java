package net.torrydev.stockmanagementsystem.repositories;

import net.torrydev.stockmanagementsystem.dto.ProductDto;

import java.util.List;
import java.util.Optional;

/**
 * Operations to perform on Products Table
 */
public interface IProductRepository {
    Optional<ProductDto> findById(Long id);

    Optional<ProductDto> findByProductCode(String prodCode);

    Optional<ProductDto> findByName(String name);

    List<ProductDto> findAllProducts();

    List<ProductDto> findProductsByPrice(double price);

    List<ProductDto> findProductsByScale(String scale);

    List<ProductDto> findProductsByVendor(String vendor);


}
