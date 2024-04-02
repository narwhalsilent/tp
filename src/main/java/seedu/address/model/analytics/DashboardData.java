package seedu.address.model.analytics;

import java.time.LocalDate;

import seedu.address.model.person.Analytics;

import java.time.ZoneId;
import java.util.Date;

/**
 * Represents the analytics data of the dashboard with 3 values
 * - Analytics object to be displayed
 * - Max loan value
 * - Earliest return date
 */
public class DashboardData {
    private Analytics analytics;
    private float maxLoanValue;
    private Date earliestReturnDate;

    public DashboardData(Analytics analytics, float maxLoanValue, Date earliestReturnDate) {
        this.analytics = analytics;
        this.maxLoanValue = maxLoanValue;
        this.earliestReturnDate = earliestReturnDate;
    }

    public Analytics getAnalytics() {
        return analytics;
    }

    public float getMaxLoanValue() {
        return maxLoanValue;
    }

    /**
     * Calculates the impact index of the dashboard data
     * Impact index is calculated as the ratio of the average loan value to the maximum loan value
     *
     * @return impact index between 0 and 1
     */
    public float getImpactIndex() {
        return analytics.getAverageLoanValue() / maxLoanValue;
    }

    /**
     * Calculates the urgency index of the dashboard data
     * Urgency index is calculated as the ratio of the number of days between the earliest return date and the current date
     * to the number of days between the earliest return date and the benchmark date
     *
     * @return urgency index between 0 and 1
     */
    public float getUrgencyIndex() {
        // Should take extra measures to ensure no overdue loans are used for calculations
        LocalDate target = analytics.getEarliestReturnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate benchmark = this.earliestReturnDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        long dayDiffBenchmark = benchmark.toEpochDay() - now.toEpochDay();
        long dayDiffTarget = target.toEpochDay() - now.toEpochDay();
        return (float) dayDiffBenchmark / dayDiffTarget;
    }
}
