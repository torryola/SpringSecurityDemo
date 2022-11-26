package net.torrydev.stockmanagementsystem.mapper;

import net.torrydev.stockmanagementsystem.model.Products;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.torrydev.stockmanagementsystem.constants.DatabaseRows.ProductsTable_Fields.*;

/**
 * Mapper Impl for converting ResultSet rows to Object i.e. Sql-Rows -> products
 */
public class ProductMapper implements RowMapper<Products> {
    @Override
    public Products mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Products.builder().productCode(rs.getString(PRODUCT_CODE))
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
