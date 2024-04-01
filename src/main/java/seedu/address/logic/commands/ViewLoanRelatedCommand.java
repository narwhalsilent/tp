package seedu.address.logic.commands;

public abstract class ViewLoanRelatedCommand extends Command {
    final boolean isShowAllLoans;

    protected ViewLoanRelatedCommand(boolean isShowAllLoans) {
        this.isShowAllLoans = isShowAllLoans;
    }
}
