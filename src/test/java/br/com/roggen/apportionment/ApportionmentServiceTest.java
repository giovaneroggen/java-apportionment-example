package br.com.roggen.apportionment;

import br.com.roggen.apportionment.dto.ProductPrice;
import br.com.roggen.apportionment.dto.Purchase;
import br.com.roggen.apportionment.service.ApportionmentService;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static br.com.roggen.apportionment.util.BigDecimalUtils.bd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ApportionmentServiceTest {

    private ApportionmentService service = new ApportionmentService();

    @Test
    public void test(){
        BigDecimal nominalValue = bd(10.0);
        Purchase purchase = this.validPurchase(nominalValue);
        BigDecimal oldTotal = purchase.getTotal();

        this.service.apportion(purchase);

        BigDecimal totalNew = purchase.getTotal();

        assertNotEquals(oldTotal, totalNew);
        assertEquals(purchase.getNominalValue(), totalNew);
    }

    private Purchase validPurchase(BigDecimal nominalValue) {
        List<ProductPrice> list = new ArrayList<>();
        list.add(
            ProductPrice.builder()
                    .id(1L)
                    .quantity(3L)
                    .price(bd(3.34))
                    .build()
        );
        return new Purchase(nominalValue, list);
    }


}
