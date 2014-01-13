package su.ugatu.moodle.is.credit_calc;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * @author: rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 19:30
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "banks")
public class BankFactory {

    private static final BankFactory INSTANCE;
    private static final String BANKS_INFO_FILE_PATH = "LendingBanks.xml";

    static {
        ClassLoader contextClassLoader
                = Thread.currentThread().getContextClassLoader();
        URL url = contextClassLoader.getResource(BANKS_INFO_FILE_PATH);
        @SuppressWarnings("all")
        File file = new File(url.getPath());
        INSTANCE = unmarshal(file);
    }

    private static BankFactory unmarshal(File file) {
        return JAXB.unmarshal(file, BankFactory.class);
    }

    public static BankFactory getInstance() {
        return INSTANCE;
    }

    @XmlElement(name = "bank", type = LendingBankImpl.class)
    private List<LendingBankImpl> banks;

    public List<LendingBankImpl> getBanks() {
        return banks;
    }

}
