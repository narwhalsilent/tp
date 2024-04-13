package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.person.Loan;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalLoans {

    public static final Loan ACTIVE_NON_OVERDUE_LOAN = new LoanBuilder().build();
    public static final Loan ACTIVE_OVERDUE_LOAN = new LoanBuilder().withId(9998)
        .withValue(new BigDecimal("1000.00")).withStartDate("2020-06-01").withReturnDate("2021-01-01")
        .withAssignee(BOB).build();
    public static final Loan INACTIVE_LOAN = new LoanBuilder().withId(9997)
        .withValue(new BigDecimal("1123.00")).withStartDate("2020-06-01").withReturnDate("2040-12-01")
        .withAssignee(CARL).withIsReturned(true).build();

    private TypicalLoans() {} // prevents instantiation

    public static List<Loan> getTypicalLoans() {
        return new ArrayList<>(Arrays.asList(ACTIVE_NON_OVERDUE_LOAN, ACTIVE_OVERDUE_LOAN, INACTIVE_LOAN));
    }

}
