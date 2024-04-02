package seedu.address.model.analytics;

import seedu.address.logic.commands.AnalyticsCommand;
import seedu.address.model.person.Analytics;

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

    public Date getEarliestReturnDate() {
        return earliestReturnDate;
    }
}
