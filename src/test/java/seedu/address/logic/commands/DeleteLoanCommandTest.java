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

public class DeleteLoanCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private final int loanListSize = model.getSortedLoanList().size();

    private final Loan firstLoan = model.getSortedLoanList().get(0);

    @Test
    public void execute_loanDeleted_success() throws CommandException {
        assertTrue(model.getSortedLoanList().contains(firstLoan));

        DeleteLoanCommand deleteLoanCommand = new DeleteLoanCommand(Index.fromOneBased(1));
        CommandResult commandResult = deleteLoanCommand.execute(model);
        int newSize = model.getSortedLoanList().size();

        assertEquals(loanListSize - 1, newSize);
        assertFalse(model.getSortedLoanList().contains(firstLoan));
        assertEquals(String.format(DeleteLoanCommand.MESSAGE_SUCCESS, firstLoan),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidLoanIndex_failure() {
        DeleteLoanCommand deleteLoanCommand = new DeleteLoanCommand(Index.fromOneBased(loanListSize + 1));
        assertCommandFailure(deleteLoanCommand, model, String.format(DeleteLoanCommand.MESSAGE_FAILURE_LOAN,
                loanListSize + 1));
    }

    @Test
    public void equals() {
        DeleteLoanCommand deleteLoanFirstCommand = new DeleteLoanCommand(Index.fromOneBased(1));
        DeleteLoanCommand deleteLoanSecondCommand = new DeleteLoanCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(deleteLoanFirstCommand.equals(deleteLoanFirstCommand));

        // same values -> returns true
        DeleteLoanCommand deleteLoanFirstCommandCopy = new DeleteLoanCommand(Index.fromOneBased(1));
        assertTrue(deleteLoanFirstCommand.equals(deleteLoanFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteLoanFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteLoanFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteLoanFirstCommand.equals(deleteLoanSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        DeleteLoanCommand deleteLoanCommand = new DeleteLoanCommand(index);
        String expected = DeleteLoanCommand.class.getCanonicalName() + "{loanIndex=" + index + "}";
        assertEquals(expected, deleteLoanCommand.toString());
    }
}
