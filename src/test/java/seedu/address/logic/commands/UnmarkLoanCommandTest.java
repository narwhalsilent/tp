package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersonsWithLoans.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Loan;

public class UnmarkLoanCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private final int loanListSize = model.getSortedLoanList().size();

    private final Loan firstLoan = model.getSortedLoanList().get(0);

    @Test
    public void execute_unmarkLoanAsReturned_success() throws CommandException {
        MarkLoanCommand markLoanCommand = new MarkLoanCommand(Index.fromOneBased(1));
        markLoanCommand.execute(model);

        assertTrue(firstLoan.isReturned());

        UnmarkLoanCommand unmarkLoanCommand = new UnmarkLoanCommand(Index.fromOneBased(1));
        CommandResult commandResult = unmarkLoanCommand.execute(model);

        assertTrue(firstLoan.isActive());
        assertEquals(String.format(unmarkLoanCommand.MESSAGE_SUCCESS, firstLoan),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidLoanIndex_failure() {
        UnmarkLoanCommand unmarkLoanCommand = new UnmarkLoanCommand(Index.fromOneBased(loanListSize + 1));
        assertCommandFailure(unmarkLoanCommand, model, String.format(unmarkLoanCommand.MESSAGE_FAILURE_LOAN,
                loanListSize + 1));
    }

    @Test
    public void equals() {
        UnmarkLoanCommand unmarkLoanFirstCommand = new UnmarkLoanCommand(Index.fromOneBased(1));
        UnmarkLoanCommand unmarkLoanSecondCommand = new UnmarkLoanCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(unmarkLoanFirstCommand.equals(unmarkLoanFirstCommand));

        // same values -> returns true
        UnmarkLoanCommand unmarkLoanFirstCommandCopy = new UnmarkLoanCommand(Index.fromOneBased(1));
        assertTrue(unmarkLoanFirstCommand.equals(unmarkLoanFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkLoanFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmarkLoanFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unmarkLoanFirstCommand.equals(unmarkLoanSecondCommand));
    }
}
