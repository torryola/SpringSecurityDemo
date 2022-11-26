package net.torrydev.stockmanagementsystem.services;

import net.torrydev.stockmanagementsystem.constants.DatabaseRows;
import net.torrydev.stockmanagementsystem.repositories.IProductRepository;
import net.torrydev.stockmanagementsystem.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static net.torrydev.stockmanagementsystem.constants.DatabaseRows.ProductsTable_Fields.*;

@Service
public class ProductDaoService implements IProductRepository {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Optional<ProductDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ProductDto> findByProductCode(String prodCode) {
        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM products WHERE "+DatabaseRows.ProductsTable_Fields.PRODUCT_CODE+" = :"+DatabaseRows.ProductsTable_Fields.PRODUCT_CODE,
                        new MapSqlParameterSource(DatabaseRows.ProductsTable_Fields.PRODUCT_CODE, prodCode),
                (rs, rowNum) -> Optional.of(ProductDto.builder().productCode(rs.getString(PRODUCT_CODE))
                        .productName(rs.getString(PRODUCT_NAME))
                        .productDescription(rs.getString(PRODUCT_DESC))
                        .productLine(rs.getString(PRODUCT_LINE))
                        .productScale(rs.getString(PRODUCT_SCALE))
                        .quantityInStock(rs.getInt(QUANTITY_IN_STOCK))
                        .buyPrice(rs.getDouble(BUY_PRICE))
                        .MSRP(rs.getDouble(MSRP))
                        .build()));
    }

    @Override
    public Optional<ProductDto> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<ProductDto> findAllProducts() {
        return null;
    }

    @Override
    public List<ProductDto> findProductsByPrice(double price) {
        return null;
    }

    @Override
    public List<ProductDto> findProductsByScale(String scale) {
        return null;
    }

    @Override
    public List<ProductDto> findProductsByVendor(String vendor) {
        return null;
    }
}
