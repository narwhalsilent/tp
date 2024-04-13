package seedu.address.model.analytics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Analytics;
import seedu.address.model.person.Loan;
import seedu.address.model.person.UniqueLoanList;
import seedu.address.testutil.LoanBuilder;


public class DashboardDataTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DashboardData(null,
                new BigDecimal("100"), new Date()));
    }

    @Test
    public void constructor_overdueReturnDate_throwsIllegalArgumentException() {
        Analytics analytics = Analytics.getAnalytics(new UniqueLoanList().asUnmodifiableObservableList());
        // Earliest return date in whole database is earlier than now
        // Loan is overdue and should not be considered
        Date oneDayBeforeNow = new Date(new Date().getTime() - 86400000);
        assertThrows(IllegalArgumentException.class, () -> new DashboardData(analytics,
                new BigDecimal("100"), oneDayBeforeNow));
    }

    @Test
    public void urgencyTest() {
        Date oneWeekAfterNow = new Date(new Date().getTime() + 604800000);
        Date twoWeeksAfterNow = new Date(new Date().getTime() + 1209600000);
        Loan l1 = new LoanBuilder().withId(1).withReturnDate(oneWeekAfterNow).build();
        Loan l2 = new LoanBuilder().withId(2).withReturnDate(twoWeeksAfterNow).build();
        Loan l3 = new LoanBuilder().withId(3).withReturnDate(twoWeeksAfterNow).build();

        UniqueLoanList loanList = new UniqueLoanList();
        loanList.addLoan(l1);
        loanList.addLoan(l2);
        loanList.addLoan(l3);

        Analytics a1 = Analytics.getAnalytics(loanList.asUnmodifiableObservableList());
        // Earliest return date in whole database is today
        // Urgency index should be zero
        DashboardData dd1 = new DashboardData(a1, new BigDecimal("100"), new Date());
        assertEquals(0, dd1.getUrgencyIndex());

        // Earliest return date in whole database is one week after now
        // Since l1 has the same due date, urgency should be 100%
        DashboardData dd2 = new DashboardData(a1, new BigDecimal("100"), oneWeekAfterNow);
        assertEquals(1, dd2.getUrgencyIndex());

        // Return loan 1
        l1.markAsReturned();
        // Regenerate analytics
        Analytics a2 = Analytics.getAnalytics(loanList.asUnmodifiableObservableList());
        // Earliest return date in whole database is 2 week after now
        DashboardData dd3 = new DashboardData(a2, new BigDecimal("100"), oneWeekAfterNow);
        assertEquals((float) 0.5, dd3.getUrgencyIndex());
    }

    @Test
    public void impactTest() {
        Date oneWeekBeforeNow = new Date(new Date().getTime() - 604800000);
        Loan l1 = new LoanBuilder().withId(1).withValue(new BigDecimal("100")).withReturnDate(oneWeekBeforeNow).build();
        Loan l2 = new LoanBuilder().withId(2).withValue(new BigDecimal("200")).withReturnDate(oneWeekBeforeNow).build();
        Loan l3 = new LoanBuilder().withId(3).withValue(new BigDecimal("300")).withReturnDate(oneWeekBeforeNow).build();

        UniqueLoanList loanList = new UniqueLoanList();
        loanList.addLoan(l1);
        loanList.addLoan(l2);
        loanList.addLoan(l3);

        Analytics a1 = Analytics.getAnalytics(loanList.asUnmodifiableObservableList());
        DashboardData dd1 = new DashboardData(a1, new BigDecimal("300"), new Date());
        assertEquals(new BigDecimal("0.67"), dd1.getImpactIndex());

        // return all loans
        l1.markAsReturned();
        l2.markAsReturned();
        l3.markAsReturned();

        // Regenerate analytics
        Analytics a2 = Analytics.getAnalytics(loanList.asUnmodifiableObservableList());
        DashboardData dd2 = new DashboardData(a2, new BigDecimal("300"), new Date());
        // Should not change since returned loans should not affect impact index
        assertEquals(new BigDecimal("0.67"), dd2.getImpactIndex());

    }
}
