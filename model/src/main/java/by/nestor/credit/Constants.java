package by.nestor.credit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;

/**
 * Константы приложения.
 *
 * @author rinat.enikeev@gmail.com
 *         Date: 22.01.14
 *         Time: 20:46
 */
public class Constants {
    public static final int NUMBER_OF_MONTHS = Month.values().length;
    // количество знаков после запятой, учитываемых при расчетах
    public static int CALC_SCALE = 64;

    // метод округления, используемый при расчетах
    public static RoundingMode ROUNDING_MODE = RoundingMode.CEILING;

    // количество знаков после запятой, выводимых в результат расчета для сумм
    public static int OUTPUT_AMOUNT_SCALE = 2;

    // количество знаков после запятой, выводимых в результат процента ЭПС
    public static int OUTPUT_PERCENT_SCALE = 4;

    public static BigDecimal round(BigDecimal interest) {
        return round(interest, CALC_SCALE);
    }

    public static BigDecimal round(BigDecimal value, int scale) {
        return value.setScale(scale, ROUNDING_MODE);
    }
}
