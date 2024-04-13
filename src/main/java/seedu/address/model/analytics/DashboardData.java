package seedu.address.model.analytics;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import seedu.address.model.person.Analytics;


/**
 * Represents the analytics data of the dashboard with 3 values
 * - Analytics object to be displayed
 * - Max loan value
 * - Earliest return date
 */
public class DashboardData {
    private Analytics analytics;
    private BigDecimal maxLoanValue;
    private Date earliestReturnDate;

    /**
     * Creates a DashboardData object with the given analytics, max loan value and earliest return date
     *
     * @param analytics analytics object to be displayed
     * @param maxLoanValue maximum loan value of all loans
     * @param earliestReturnDate earliest return date of all loans (not returned and not overdue)
     */
    public DashboardData(Analytics analytics, BigDecimal maxLoanValue, Date earliestReturnDate) {
        requireNonNull(analytics);
        requireNonNull(maxLoanValue);
        this.analytics = analytics;
        this.maxLoanValue = maxLoanValue;
        // Should never be over the current date since overdue loans are not included
        if (earliestReturnDate.before(new Date())) {
            throw new IllegalArgumentException("Earliest return date should be in the future");
        }
        this.earliestReturnDate = earliestReturnDate;
    }

    public Analytics getAnalytics() {
        return analytics;
    }

    public BigDecimal getMaxLoanValue() {
        return maxLoanValue;
    }

    /**
     * Calculates the impact index of the dashboard data
     * Impact index is calculated as the ratio of the average loan value to the maximum loan value
     * to 2 decimal places.
     *
     * @return impact index between 0 and 1
     */
    public BigDecimal getImpactIndex() {
        return analytics.getAverageLoanValue().divide(maxLoanValue, 2, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the urgency index of the dashboard data
     * Urgency index is calculated as the ratio of the number of days between the earliest return date and the current
     * to the number of days between the earliest return date and the benchmark date
     *
     * @return urgency index between 0 and 1
     */
    public Float getUrgencyIndex() {
        // Should take extra measures to ensure no overdue loans are used for calculations
        if (analytics.getEarliestReturnDate() == null || earliestReturnDate == null) {
            return null;
        }

        LocalDate target = analytics.getEarliestReturnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate benchmark = this.earliestReturnDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        long dayDiffBenchmark = benchmark.toEpochDay() - now.toEpochDay();
        long dayDiffTarget = target.toEpochDay() - now.toEpochDay();
        if (dayDiffTarget == 0) {
            return 1.0f;
        }
        return (float) dayDiffBenchmark / dayDiffTarget;
    }

    @Override
    public String toString() {
        return "Analytics: " + analytics + ", Max Loan Value: " + maxLoanValue + ", Earliest Return Date: "
                + earliestReturnDate;
    }
}
