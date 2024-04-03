package seedu.address.logic.commands;

/**
 * Represents a command that is related to viewing loans.
 */
public abstract class ViewLoanRelatedCommand extends Command {
    public static final String COMMAND_WORD = "viewloan";
    final boolean isShowAllLoans;

    protected ViewLoanRelatedCommand(boolean isShowAllLoans) {
        this.isShowAllLoans = isShowAllLoans;
    }
}
