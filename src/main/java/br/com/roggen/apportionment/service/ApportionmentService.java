package br.com.roggen.apportionment.service;

import br.com.roggen.apportionment.dto.ProductPrice;
import br.com.roggen.apportionment.dto.Purchase;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static br.com.roggen.apportionment.util.BigDecimalUtils.bd;
import static br.com.roggen.apportionment.util.BigDecimalUtils.firstValueIsMaior;

public class ApportionmentService {

    public void apportion(Purchase purchase){
        BigDecimal total = purchase.getTotal();
        BigDecimal nominalValue = purchase.getNominalValue();
        BigDecimal valueForSubtract = bd(0.01);

        while(firstValueIsMaior(total, nominalValue)){
            ProductPrice productPrice = this.anyProductThatWontAffectValueForSubtractOrRandom(purchase.getProductPriceList(), valueForSubtract, total, nominalValue);
            BigDecimal newTotal = this.totalAfterProductPriceSubtraction(total, productPrice, valueForSubtract);
            if(firstValueIsMaior(nominalValue, newTotal)){
                valueForSubtract = valueForSubtract.divide(BigDecimal.TEN);
                continue;
            }else{
                this.subtractProductPrice(productPrice, valueForSubtract);
                total = purchase.getTotal();
            }
        }
    }

    private BigDecimal totalAfterProductPriceSubtraction(BigDecimal total, ProductPrice productPrice, BigDecimal initialValueForSubtract) {
        BigDecimal realValueToSubtract = initialValueForSubtract.multiply(productPrice.getQuantityBd());
        return total.subtract(realValueToSubtract);
    }

    private void subtractProductPrice(ProductPrice r, BigDecimal valueForSubtract) {
        BigDecimal price = r.getPrice().subtract(valueForSubtract);
        r.setPrice(price);
    }

    private ProductPrice anyProductThatWontAffectValueForSubtractOrRandom(List<ProductPrice> list, BigDecimal valueForSubtract, BigDecimal total, BigDecimal nominalValue) {
        List<ProductPrice> collect1 = this.productListWithPriceMinorThan(list, valueForSubtract);
        List<ProductPrice> collect2 = this.productListThatWontAffectValueForSubtract(collect1, total, nominalValue, valueForSubtract);
        if(collect2.isEmpty()){
            return this.random(collect1);
        }else{
            return this.random(collect2);
        }
    }

    private List<ProductPrice> productListThatWontAffectValueForSubtract(List<ProductPrice> collect1, BigDecimal total, BigDecimal nominalValue, BigDecimal valueForSubtract) {
        return collect1.stream()
                       .filter(it -> {
                           BigDecimal fakeNewTotal = this.totalAfterProductPriceSubtraction(total, it, valueForSubtract);
                           return !firstValueIsMaior(nominalValue, fakeNewTotal);
                       }).collect(Collectors.toList());
    }

    private List<ProductPrice> productListWithPriceMinorThan(List<ProductPrice> list, BigDecimal valueForSubtract) {
        return list.stream()
                   .filter(it -> firstValueIsMaior(it.getPrice(), valueForSubtract))
                   .collect(Collectors.toList());
    }

    private <T> T random(List<T> list) {
        Random r = new Random();
        int i = r.nextInt(list.size());
        return list.get(i);
    }
}
