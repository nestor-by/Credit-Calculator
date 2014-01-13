package su.ugatu.moodle.is.credit_calc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 21:47
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "creditOffer")
public class CreditOfferImpl implements CreditOffer {

    @XmlElement
    private String name;
    @XmlElement
    private Double minAmount;
    @XmlElement
    private Double maxAmount;
    @XmlElement
    private Double minRate;
    @XmlElement
    private String currencyName;

    public String getName() {
        return name;
    }

}
