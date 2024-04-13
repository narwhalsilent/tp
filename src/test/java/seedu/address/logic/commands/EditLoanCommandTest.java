package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_RETURN_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_RETURN_DATE_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_RETURN_DATE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_START_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_START_DATE_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_START_DATE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_VALUE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_VALUE_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_VALUE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersonsWithLoans.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditLoanCommand.EditLoanDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Loan;

public class EditLoanCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private final int loanListSize = model.getSortedLoanList().size();

    @Test
    public void execute_allFieldsSpecified_success() throws CommandException {
        EditLoanDescriptor editLoanDescriptor = new EditLoanDescriptor();
        editLoanDescriptor.setValue(VALID_LOAN_VALUE_ONE);
        editLoanDescriptor.setStartDate(VALID_LOAN_START_DATE_ONE);
        editLoanDescriptor.setReturnDate(VALID_LOAN_RETURN_DATE_ONE);

        EditLoanCommand editLoanCommand = new EditLoanCommand(editLoanDescriptor, Index.fromOneBased(1));
        CommandResult commandResult = editLoanCommand.execute(model);
        // Note that as VALID_LOAN_START_DATE_ONE is before all the other dates,
        // the edited loan is still first in the sorted loan list
        Loan editedLoan = model.getSortedLoanList().get(0);

        assertEquals(editedLoan.getValue(), VALID_LOAN_VALUE_ONE);
        assertEquals(editedLoan.getStartDate(), VALID_LOAN_START_DATE_ONE);
        assertEquals(editedLoan.getReturnDate(), VALID_LOAN_RETURN_DATE_ONE);
        assertEquals(String.format(EditLoanCommand.MESSAGE_SUCCESS, editedLoan), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_onlyValueSpecified_success() throws CommandException {
        EditLoanDescriptor editLoanDescriptor = new EditLoanDescriptor();
        editLoanDescriptor.setValue(VALID_LOAN_VALUE_ONE);

        EditLoanCommand editLoanCommand = new EditLoanCommand(editLoanDescriptor, Index.fromOneBased(1));
        CommandResult commandResult = editLoanCommand.execute(model);

        Loan editedLoan = model.getSortedLoanList().get(0);

        assertEquals(editedLoan.getValue(), VALID_LOAN_VALUE_ONE);
        assertEquals(String.format(EditLoanCommand.MESSAGE_SUCCESS, editedLoan), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_onlyStartDateSpecified_success() throws CommandException {
        EditLoanDescriptor editLoanDescriptor = new EditLoanDescriptor();
        editLoanDescriptor.setStartDate(VALID_LOAN_START_DATE_ONE);

        EditLoanCommand editLoanCommand = new EditLoanCommand(editLoanDescriptor, Index.fromOneBased(1));
        CommandResult commandResult = editLoanCommand.execute(model);

        Loan editedLoan = model.getSortedLoanList().get(0);

        assertEquals(editedLoan.getStartDate(), VALID_LOAN_START_DATE_ONE);
        assertEquals(String.format(EditLoanCommand.MESSAGE_SUCCESS, editedLoan), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_onlyReturnDateSpecified_success() throws CommandException {
        EditLoanDescriptor editLoanDescriptor = new EditLoanDescriptor();
        editLoanDescriptor.setReturnDate(VALID_LOAN_RETURN_DATE_ONE);

        EditLoanCommand editLoanCommand = new EditLoanCommand(editLoanDescriptor, Index.fromOneBased(1));
        CommandResult commandResult = editLoanCommand.execute(model);

        Loan editedLoan = model.getSortedLoanList().get(0);

        assertEquals(editedLoan.getReturnDate(), VALID_LOAN_RETURN_DATE_ONE);
        assertEquals(String.format(EditLoanCommand.MESSAGE_SUCCESS, editedLoan), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidLoanIndex_failure() {
        EditLoanDescriptor editLoanDescriptor = new EditLoanDescriptor();
        editLoanDescriptor.setValue(VALID_LOAN_VALUE_ONE);
        editLoanDescriptor.setStartDate(VALID_LOAN_START_DATE_ONE);
        editLoanDescriptor.setReturnDate(VALID_LOAN_RETURN_DATE_ONE);

        EditLoanCommand editLoanCommand = new EditLoanCommand(editLoanDescriptor,
                Index.fromOneBased(loanListSize + 1));
        assertCommandFailure(editLoanCommand, model, String.format(EditLoanCommand.MESSAGE_FAILURE_LOAN,
                loanListSize + 1));
    }

    @Test
    public void equals() {
        EditLoanDescriptor editLoanDescriptor = new EditLoanDescriptor();
        editLoanDescriptor.setValue(VALID_LOAN_VALUE_ONE);
        editLoanDescriptor.setStartDate(VALID_LOAN_START_DATE_ONE);
        editLoanDescriptor.setReturnDate(VALID_LOAN_RETURN_DATE_ONE);
        EditLoanDescriptor copyDescriptor = new EditLoanDescriptor();
        copyDescriptor.setValue(VALID_LOAN_VALUE_ONE);
        copyDescriptor.setStartDate(VALID_LOAN_START_DATE_ONE);
        copyDescriptor.setReturnDate(VALID_LOAN_RETURN_DATE_ONE);

        final EditLoanCommand standardCommand = new EditLoanCommand(editLoanDescriptor,
                Index.fromOneBased(1));
        EditLoanCommand commandWithSameValues = new EditLoanCommand(copyDescriptor, Index.fromOneBased(1));

        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditLoanCommand(editLoanDescriptor,
                Index.fromOneBased(2))));

        // different descriptor -> returns false
        EditLoanDescriptor differentDescriptor = new EditLoanDescriptor();
        differentDescriptor.setValue(VALID_LOAN_VALUE_TWO);
        differentDescriptor.setStartDate(VALID_LOAN_START_DATE_TWO);
        differentDescriptor.setReturnDate(VALID_LOAN_RETURN_DATE_TWO);
        assertFalse(standardCommand.equals(new EditLoanCommand(differentDescriptor,
                Index.fromOneBased(1))));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditLoanDescriptor editLoanDescriptor = new EditLoanDescriptor();
        editLoanDescriptor.setValue(VALID_LOAN_VALUE_THREE);
        editLoanDescriptor.setStartDate(VALID_LOAN_START_DATE_THREE);
        editLoanDescriptor.setReturnDate(VALID_LOAN_RETURN_DATE_THREE);
        EditLoanCommand editLoanCommand = new EditLoanCommand(editLoanDescriptor, Index.fromOneBased(1));
        String expected = EditLoanCommand.class.getCanonicalName() + "{loanIndex=" + index
                + ", editedDetails=" + editLoanDescriptor + "}";
        assertEquals(expected, editLoanCommand.toString());
    }
}
