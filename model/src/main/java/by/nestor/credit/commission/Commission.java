package by.nestor.credit.commission;

import by.nestor.credit.CreditApplication;
import by.nestor.credit.Frequency;

import java.math.BigDecimal;

import static by.nestor.credit.Constants.OUTPUT_AMOUNT_SCALE;
import static by.nestor.credit.Constants.round;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public class Commission {
    private final CommissionType type;
    private final Frequency frequency;
    private final BigDecimal value;

    public Commission(final CommissionType type, final BigDecimal value) {
        this(type, null, value);
    }

    public Commission(final CommissionType type, final Frequency frequency, final BigDecimal value) {
        this.type = type;
        this.frequency = frequency;
        this.value = value;
    }

    public CommissionType getType() {
        return type;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal calculate(final CreditApplication application) {
        return round(getValue(application.getAmount()), OUTPUT_AMOUNT_SCALE);
    }

    private BigDecimal getValue(BigDecimal creditAmount) {
        switch (type) {
            case AMOUNT:
                return value;
            case PERCENT:
                return creditAmount.multiply(value);
        }
        throw new UnsupportedOperationException(type + " type is not supported");
    }
}
