package su.ugatu.moodle.is.credit_calc;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 18:50
 */
class CreditProposalImpl implements CreditProposal {
    private final CreditApplication application;

    public CreditProposalImpl(final CreditApplication application) {
        this.application = application;
    }
}
