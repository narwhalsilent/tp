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


/**
 * Contains integration tests (interaction with the Model) and unit tests for MarkLoanCommand.
 */
public class MarkLoanCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private final int loanListSize = model.getSortedLoanList().size();

    private final Loan firstLoan = model.getSortedLoanList().get(0);

    @Test
    public void execute_markLoanAsReturned_success() throws CommandException {
        assertTrue(firstLoan.isActive());

        MarkLoanCommand markLoanCommand = new MarkLoanCommand(Index.fromOneBased(1));
        CommandResult commandResult = markLoanCommand.execute(model);

        assertTrue(firstLoan.isReturned());
        assertEquals(String.format(MarkLoanCommand.MESSAGE_SUCCESS, firstLoan),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidLoanIndex_failure() {
        MarkLoanCommand markLoanCommand = new MarkLoanCommand(Index.fromOneBased(loanListSize + 1));
        assertCommandFailure(markLoanCommand, model, String.format(MarkLoanCommand.MESSAGE_FAILURE_LOAN,
                loanListSize + 1));
    }

    @Test
    public void equals() {
        MarkLoanCommand markLoanFirstCommand = new MarkLoanCommand(Index.fromOneBased(1));
        MarkLoanCommand markLoanSecondCommand = new MarkLoanCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(markLoanFirstCommand.equals(markLoanFirstCommand));

        // same values -> returns true
        MarkLoanCommand markLoanFirstCommandCopy = new MarkLoanCommand(Index.fromOneBased(1));
        assertTrue(markLoanFirstCommand.equals(markLoanFirstCommandCopy));

        // different types -> returns false
        assertFalse(markLoanFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markLoanFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(markLoanFirstCommand.equals(markLoanSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        MarkLoanCommand markLoanCommand = new MarkLoanCommand(index);
        String expected = MarkLoanCommand.class.getCanonicalName() + "{loanIndex=" + index + "}";
        assertEquals(expected, markLoanCommand.toString());
    }
}
