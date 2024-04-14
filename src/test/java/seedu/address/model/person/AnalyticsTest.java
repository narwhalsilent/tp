package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LoanBuilder;

public class AnalyticsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // Null loan list should throw NullPointerException
        assertThrows(NullPointerException.class, () -> Analytics.getAnalytics(null));
    }

    @Test
    public void emptyLoanList() {
        // Empty loan list should not throw any exceptions
        Analytics test = Analytics.getAnalytics(new UniqueLoanList().asUnmodifiableObservableList());
        assertEquals(0, test.getNumActiveLoans()); // No active loans
        assertEquals(0, test.getPropOverdueLoans()); // Proportion of overdue loans is 0 (default)
        assertEquals(0, test.getPropActiveLoans()); // Proportion of active loans is 0 (default)
        assertEquals(BigDecimal.ZERO, test.getAverageLoanValue()); // Average loan value is 0 (default)
        assertNull(test.getEarliestReturnDate()); // Earliest loan date is null
    }

    @Test
    public void singleLoan() {
        // Single loan with value 100
        Loan loan = new LoanBuilder().withValue(new BigDecimal("100.00")).build();
        UniqueLoanList loanList = new UniqueLoanList();
        loanList.addLoan(loan);
        Analytics test = Analytics.getAnalytics(loanList.asUnmodifiableObservableList());
        assertEquals(1, test.getNumActiveLoans()); // 1 active loan
        assertEquals(0, test.getPropOverdueLoans()); // Proportion of overdue loans is 0 (default)
        assertEquals(1, test.getPropActiveLoans()); // Proportion of active loans is 1
        assertEquals(new BigDecimal("100.00"), test.getAverageLoanValue()); // Average loan value is 100
        assertEquals(loan.getReturnDate(), test.getEarliestReturnDate()); // return the loan's return date
    }

    @Test
    public void multipleActiveLoans() {
        Loan loan1 = new LoanBuilder().withId(1).withValue(new BigDecimal("100.00")).build();
        Loan loan2 = new LoanBuilder().withId(2).withValue(new BigDecimal("200.00")).build();
        Loan loan3 = new LoanBuilder().withId(3).withValue(new BigDecimal("300.00")).build();
        Loan loan4 = new LoanBuilder().withId(4)
                .withValue(new BigDecimal("200.00")).withReturnDate("2010-01-01").build(); // Earliest return date
        UniqueLoanList loanList = new UniqueLoanList();
        loanList.addLoan(loan1);
        loanList.addLoan(loan2);
        loanList.addLoan(loan3);
        loanList.addLoan(loan4);
        Analytics test = Analytics.getAnalytics(loanList.asUnmodifiableObservableList());
        assertEquals(4, test.getNumActiveLoans()); // 4 active loans
        assertEquals(0.25, test.getPropOverdueLoans()); // Proportion of overdue loans is 0.25
        assertEquals(1, test.getPropActiveLoans()); // Proportion of active loans is 1
        assertEquals(new BigDecimal("200.00"), test.getAverageLoanValue()); // Average loan value is 200
        // Earliest return date should NOT be loan4's return date since it is overdue
        assertNotEquals(loan4.getReturnDate(), test.getEarliestReturnDate());
        // Earliest return date can be either 1,2 or 3's return date
        assertEquals(loan1.getReturnDate(), test.getEarliestReturnDate());
    }

    @Test
    public void withInactiveLoans() {
        Loan loan1 = new LoanBuilder().withId(1).withValue(new BigDecimal("100.00"))
                .withReturnDate("2025-01-01").build();
        Loan loan2 = new LoanBuilder().withId(2).withValue(new BigDecimal("200.00")).build();
        Loan loan3 = new LoanBuilder().withId(3).withValue(new BigDecimal("300.00")).build();
        Loan loan4 = new LoanBuilder().withId(4)
                .withValue(new BigDecimal("200.00")).withReturnDate("2010-01-01").build();
        loan4.markAsReturned();
        UniqueLoanList loanList = new UniqueLoanList();
        loanList.addLoan(loan1);
        loanList.addLoan(loan2);
        loanList.addLoan(loan3);
        loanList.addLoan(loan4);
        Analytics test = Analytics.getAnalytics(loanList.asUnmodifiableObservableList());
        assertEquals(3, test.getNumActiveLoans()); // 3 active loans
        assertEquals(0, test.getPropOverdueLoans()); // Proportion of overdue loans is 0 (loan4 is returned)
        assertEquals(0.75, test.getPropActiveLoans()); // Proportion of active loans is 1
        assertEquals(new BigDecimal("200.00"), test.getAverageLoanValue()); // Average loan value is 200
        // Earliest return date must be loan1's return date
        assertEquals(loan1.getReturnDate(), test.getEarliestReturnDate());
    }


}
