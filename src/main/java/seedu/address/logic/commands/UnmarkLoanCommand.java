package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Loan;

/**
 * Reverts the status of a loan to unpaid.
 */
public class UnmarkLoanCommand extends Command {
    public static final String COMMAND_WORD = "unmarkloan";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the loan number of current person in view as paid.\n"
            + "Parameters: INDEX\n"
            + "INDEX must be a positive integer.\n"
            + "Example: " + COMMAND_WORD + " 1 " + "l/2\n"
            + "This marks the loan of loan index 2 of the person at index 1 as paid.";
    public static final String MESSAGE_SUCCESS = "Loan unmarked.\n"
            + "Loan: %1$s";
    public static final String MESSAGE_FAILURE_LOAN = "No loan has been found "
            + "for loan number: %1$d";
    private final Index loanIndex;

    /**
     * Creates a UnmarkLoanCommand to delete the specified loan.
     * @param loanIndex
     */
    public UnmarkLoanCommand(Index loanIndex) {
        requireAllNonNull(loanIndex);
        this.loanIndex = loanIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Loan> lastShownList = model.getSortedLoanList();
        if (loanIndex.getZeroBased() >= lastShownList.size()) {
            // in reality, it's loan index outside of list range. We will be concerned about it later.
            throw new CommandException(String.format(MESSAGE_FAILURE_LOAN, loanIndex.getOneBased()));
        }
        // delete specified loan number
        Loan loanToUnmark = lastShownList.get(loanIndex.getZeroBased());
        model.unmarkLoan(loanToUnmark);
        return new CommandResult(generateSuccessMessage(loanToUnmark), false, false, true);
    }

    /**
     * Generates a command execution success message after loan is deleted from the
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Loan markedLoan) {
        return String.format(MESSAGE_SUCCESS, markedLoan);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof UnmarkLoanCommand)) {
            return false;
        }
        UnmarkLoanCommand e = (UnmarkLoanCommand) other;
        return loanIndex.equals(e.loanIndex);
    }
}
