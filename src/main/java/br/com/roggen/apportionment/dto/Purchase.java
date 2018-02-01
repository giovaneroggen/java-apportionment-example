package br.com.roggen.apportionment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Data
@AllArgsConstructor
public class Purchase {

    private BigDecimal nominalValue;
    private List<ProductPrice> productPriceList;

    public BigDecimal getTotal() {
        return this.productPriceList
                   .stream()
                   .map(ProductPrice::total)
                   .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
    }
}
