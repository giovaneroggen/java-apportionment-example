package br.com.roggen.apportionment.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

import static br.com.roggen.apportionment.util.BigDecimalUtils.bd;

@Data
@Builder
public class ProductPrice {

    private Long id;
    private Long quantity;
    private BigDecimal price;

    public BigDecimal total() {
        return this.price.multiply(getQuantityBd());
    }

    public BigDecimal getQuantityBd() {
        return bd(this.quantity.doubleValue());
    }
}
