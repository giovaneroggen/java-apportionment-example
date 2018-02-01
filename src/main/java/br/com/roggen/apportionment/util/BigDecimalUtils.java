package br.com.roggen.apportionment.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {

    public static BigDecimal bd(Double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
    }

    public static boolean firstValueIsMaior(BigDecimal total, BigDecimal paid) {
        return total.compareTo(paid) == 1;
    }
}
