package su.ugatu.moodle.is.credit_calc.bank;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 19:30
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "banks")
public class BankFactory {

    private static final BankFactory INSTANCE;
    private static final String BANKS_INFO_FILE_PATH = "LendingBanks.xml";

    @XmlTransient
    private Map<String, LendingBank> bankMap = new HashMap<String, LendingBank>();

    static {
        ClassLoader contextClassLoader
                = Thread.currentThread().getContextClassLoader();
        URL url = contextClassLoader.getResource(BANKS_INFO_FILE_PATH);
        @SuppressWarnings("all")
        File file = new File(url.getPath());
        INSTANCE = unmarshal(file);
        for (LendingBank bank : INSTANCE.getBanks()) {
            INSTANCE.bankMap.put(bank.getName(), bank);
        }
    }

    private static BankFactory unmarshal(File file) {
        return JAXB.unmarshal(file, BankFactory.class);
    }

    public static BankFactory getInstance() {
        return INSTANCE;
    }

    @XmlElement(name = "bank", type = LendingBankImpl.class)
    private Set<LendingBank> banks;

    public Collection<LendingBank> getBanks() {
        return banks;
    }

    public LendingBank getLendingBank(String name) {
        return bankMap.get(name);
    }

}
