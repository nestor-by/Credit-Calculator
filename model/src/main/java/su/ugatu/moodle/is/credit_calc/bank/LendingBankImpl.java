package su.ugatu.moodle.is.credit_calc.bank;

import su.ugatu.moodle.is.credit_calc.*;
import su.ugatu.moodle.is.credit_calc.customer.Customer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
    private Set<CreditOffer> creditOffers;

    public LendingBankImpl() {
        super(null);
    }

    @Override
    public String getName() {
        return name;
    }

    public Set<CreditOffer> getCreditOffers() {
        return creditOffers;
    }

    @Override
    public Collection<CreditProposal> getCreditProposals(final Customer customer,
                                                         final CreditApplication creditApplication) {
        Set<CreditProposal> proposals = new HashSet<CreditProposal>();
        for (CreditOffer offer : creditOffers) {
            CreditProposal creditProposal = offer.calculateProposal(customer, creditApplication);
            if (creditProposal != null) proposals.add(creditProposal);
        }
        return proposals;
    }

    @Override
    public String getDefaultCurrency() {
        return defaultCurrency;
    }
}
