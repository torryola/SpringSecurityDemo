package net.torrydev.stockmanagementsystem.mapper;

import net.torrydev.stockmanagementsystem.dto.ProductDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.torrydev.stockmanagementsystem.constants.DatabaseRows.ProductsTable_Fields.*;

public class ProductDtoMapper implements RowMapper<ProductDto> {
    @Override
    public ProductDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ProductDto.builder().productCode(rs.getString(PRODUCT_CODE))
                .productName(rs.getString(PRODUCT_NAME))
                .productDescription(rs.getString(PRODUCT_DESC))
                .productLine(rs.getString(PRODUCT_LINE))
                .productScale(rs.getString(PRODUCT_SCALE))
                .quantityInStock(rs.getInt(QUANTITY_IN_STOCK))
                .buyPrice(rs.getDouble(BUY_PRICE))
                .MSRP(rs.getDouble(MSRP))
                .build();
    }
}
