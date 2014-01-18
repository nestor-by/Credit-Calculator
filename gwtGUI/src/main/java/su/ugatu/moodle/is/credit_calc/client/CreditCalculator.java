package su.ugatu.moodle.is.credit_calc.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 19.01.14
 * Time: 1:40
 */
public class CreditCalculator implements EntryPoint {
    public void onModuleLoad() {
        CreditCalculatorView view = new CreditCalculatorView();
        RootPanel.get().add(view);
    }
}
