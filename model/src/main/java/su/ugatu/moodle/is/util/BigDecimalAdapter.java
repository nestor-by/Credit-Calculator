package su.ugatu.moodle.is.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;

/**
 * @author rinat.enikeev@gmail.com
 *         Date: 22.01.14
 *         Time: 21:30
 */
public class BigDecimalAdapter extends XmlAdapter<String, BigDecimal> {

    @Override
    public String marshal(BigDecimal value) throws Exception
    {
        if (value!= null)
        {
            return value.toString();
        }
        return null;
    }

    @Override
    public BigDecimal unmarshal(String s) throws Exception
    {
        return new BigDecimal(s);
    }
}
