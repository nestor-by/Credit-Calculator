package su.ugatu.moodle.is.credit_calc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Currency;
import java.util.List;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 18:49
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bank")
class LendingBankImpl extends BankImpl implements LendingBank {

    @XmlElement
    private String name;
    @XmlElement
    private String defaultCurrency;

    @XmlElement(name = "creditOffer", type = CreditOfferImpl.class)
    private List<CreditOffer> creditOffers;

    public LendingBankImpl() {
        super(null);
    }

    @Override
    public String getName() {
        return name;
    }

    List<CreditOffer> getCreditOffers() {
        return creditOffers;
    }

    @Override
    public Currency getDefaultCurrency() {
        return Currency.getInstance(defaultCurrency);
    }
}
