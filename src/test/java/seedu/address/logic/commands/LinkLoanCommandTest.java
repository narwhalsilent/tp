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
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.LinkLoanCommand.LinkLoanDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Loan;
import seedu.address.model.person.Person;

public class LinkLoanCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private final Person firstPerson = model.getFilteredPersonList().get(0);

    private final int personListSize = model.getFilteredPersonList().size();

    @Test
    public void execute_loanLinkedToPerson_success() throws CommandException {
        LinkLoanDescriptor linkLoanDescriptor = new LinkLoanDescriptor(VALID_LOAN_VALUE_ONE,
                VALID_LOAN_START_DATE_ONE, VALID_LOAN_RETURN_DATE_ONE);
        LinkLoanCommand linkLoanCommand = new LinkLoanCommand(linkLoanDescriptor, Index.fromOneBased(1));
        CommandResult commandResult = linkLoanCommand.execute(model);
        Loan linkedLoan = model.getSortedLoanList().get(0);

        assertEquals(linkedLoan.getValue(), VALID_LOAN_VALUE_ONE);
        assertEquals(linkedLoan.getStartDate(), VALID_LOAN_START_DATE_ONE);
        assertEquals(linkedLoan.getReturnDate(), VALID_LOAN_RETURN_DATE_ONE);
        assertEquals(linkedLoan.getAssignee(), firstPerson);
        assertEquals(String.format(LinkLoanCommand.MESSAGE_SUCCESS, firstPerson.getName(),
                        linkedLoan),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        LinkLoanDescriptor linkLoanDescriptor = new LinkLoanDescriptor(VALID_LOAN_VALUE_ONE,
                VALID_LOAN_START_DATE_ONE, VALID_LOAN_RETURN_DATE_ONE);
        LinkLoanCommand linkLoanCommand = new LinkLoanCommand(linkLoanDescriptor,
                Index.fromOneBased(personListSize + 1));
        assertCommandFailure(linkLoanCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        LinkLoanDescriptor linkLoanDescriptor = new LinkLoanDescriptor(VALID_LOAN_VALUE_ONE,
                VALID_LOAN_START_DATE_ONE, VALID_LOAN_RETURN_DATE_ONE);
        LinkLoanDescriptor copyDescriptor = new LinkLoanDescriptor(linkLoanDescriptor);
        final LinkLoanCommand standardCommand = new LinkLoanCommand(linkLoanDescriptor, Index.fromOneBased(1));
        LinkLoanCommand commandWithSameValues = new LinkLoanCommand(copyDescriptor, Index.fromOneBased(1));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new LinkLoanCommand(linkLoanDescriptor, Index.fromOneBased(2))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new LinkLoanCommand(new LinkLoanDescriptor(
                VALID_LOAN_VALUE_TWO,
                VALID_LOAN_START_DATE_TWO,
                VALID_LOAN_RETURN_DATE_TWO), Index.fromOneBased(1))));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        LinkLoanDescriptor linkLoanDescriptor = new LinkLoanDescriptor(VALID_LOAN_VALUE_THREE,
                VALID_LOAN_START_DATE_THREE, VALID_LOAN_RETURN_DATE_THREE);
        LinkLoanCommand linkLoanCommand = new LinkLoanCommand(linkLoanDescriptor, Index.fromOneBased(1));
        String expected = LinkLoanCommand.class.getCanonicalName() + "{linkTarget=" + index
                + ", loanDescription=" + linkLoanDescriptor + "}";
        assertEquals(expected, linkLoanCommand.toString());
    }
}
