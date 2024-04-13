package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalLoans.ACTIVE_NON_OVERDUE_LOAN;
import static seedu.address.testutil.TypicalLoans.INACTIVE_LOAN;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateUtil;
import seedu.address.testutil.LoanBuilder;

public class LoanTest {

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(ACTIVE_NON_OVERDUE_LOAN.equals(ACTIVE_NON_OVERDUE_LOAN));

        // null -> returns false
        assertFalse(ACTIVE_NON_OVERDUE_LOAN.equals(null));

        // same name, all other attributes different -> returns true
        Loan editedActiveNonOverdueLoan = new LoanBuilder(ACTIVE_NON_OVERDUE_LOAN).withValue(INACTIVE_LOAN.getValue())
                .withStartDate(INACTIVE_LOAN.getStartDate()).withReturnDate(INACTIVE_LOAN.getReturnDate())
                .withIsReturned(INACTIVE_LOAN.isReturned()).withAssignee(INACTIVE_LOAN.getAssignee()).build();
        assertTrue(ACTIVE_NON_OVERDUE_LOAN.equals(editedActiveNonOverdueLoan));

        // different name, all other attributes same -> returns false
        editedActiveNonOverdueLoan = new LoanBuilder(ACTIVE_NON_OVERDUE_LOAN).withId(6969).build();
        assertFalse(ACTIVE_NON_OVERDUE_LOAN.equals(editedActiveNonOverdueLoan));
    }

    @Test
    public void isValidValue() {
        // value is zero -> returns false
        assertFalse(Loan.isValidValue(BigDecimal.ZERO));

        // value is negative -> returns false
        assertFalse(Loan.isValidValue(new BigDecimal("-1.00")));

        // value is positive -> returns true
        assertTrue(Loan.isValidValue(new BigDecimal("1.00")));

        // value is very large number -> returns true
        assertTrue(Loan.isValidValue(new BigDecimal("11235638206.00")));
    }

    @Test
    public void markAsReturned() {
        Loan loanCopy = new LoanBuilder(ACTIVE_NON_OVERDUE_LOAN).build();
        loanCopy.markAsReturned();
        assertTrue(loanCopy.isReturned());
    }

    @Test
    public void unmarkAsReturned() {
        Loan loanCopy = new LoanBuilder(INACTIVE_LOAN).build();
        loanCopy.unmarkAsReturned();
        assertFalse(loanCopy.isReturned());
    }

    @Test
    public void toStringMethod() {
        String expected = String.format("$%.2f, %s, %s",
            ACTIVE_NON_OVERDUE_LOAN.getValue(),
            DateUtil.format(ACTIVE_NON_OVERDUE_LOAN.getStartDate()),
            DateUtil.format(ACTIVE_NON_OVERDUE_LOAN.getReturnDate()));
        assertEquals(expected, ACTIVE_NON_OVERDUE_LOAN.toString());

        expected = String.format("$%.2f, %s, %s (Returned)",
            INACTIVE_LOAN.getValue(),
            DateUtil.format(INACTIVE_LOAN.getStartDate()),
            DateUtil.format(INACTIVE_LOAN.getReturnDate()));
        assertEquals(expected, INACTIVE_LOAN.toString());
    }

}
