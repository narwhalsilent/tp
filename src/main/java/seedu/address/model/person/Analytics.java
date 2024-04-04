package seedu.address.model.person;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javafx.collections.ObservableList;

/**
 * Represents the analytics of a LoanRecords object.
 */
public class Analytics {

    private int numLoans; // total number of loans
    private int numOverdueLoans; // total number of overdue loans
    private int numActiveLoans; // total number of active loans

    private float propOverdueLoans; // proportion of loans that are overdue over active loans
    private float propActiveLoans; // proportion of loans that are active over total loans

    private BigDecimal totalValueLoaned; // total value of all loans
    private BigDecimal totalValueOverdue; // total value of all overdue loans
    private BigDecimal totalValueActive; // total value of all active loans

    private BigDecimal averageLoanValue; // average loan value of all loans
    private BigDecimal averageOverdueValue; // average loan value of all overdue loans
    private BigDecimal averageActiveValue; // average loan value of all active loans

    private Date earliestLoanDate; // earliest loan date of all loans
    private Date earliestReturnDate; // earliest return date of active loans
    private Date latestLoanDate; // latest loan date of all loans
    private Date latestReturnDate; // latest return date of active loans

    private Analytics() {
        this.numLoans = 0;
        this.numOverdueLoans = 0;
        this.numActiveLoans = 0;

        this.propOverdueLoans = 0;
        this.propActiveLoans = 0;

        this.totalValueLoaned = BigDecimal.ZERO;
        this.totalValueOverdue = BigDecimal.ZERO;
        this.totalValueActive = BigDecimal.ZERO;

        this.averageLoanValue = BigDecimal.ZERO;
        this.averageOverdueValue = BigDecimal.ZERO;
        this.averageActiveValue = BigDecimal.ZERO;

        this.earliestLoanDate = null;
        this.earliestReturnDate = null;
        this.latestLoanDate = null;
        this.latestReturnDate = null;
    }

    /**
     * Updates the fields that count the number of various loans.
     *
     * @param loan The loan to update the fields with.
     */
    private void updateNumFields(Loan loan) {
        this.numLoans++;
        if (loan.isOverdue()) {
            this.numOverdueLoans++;
        }
        if (loan.isActive()) {
            this.numActiveLoans++;
        }
    }

    /**
     * Updates the fields that calculate the proportion of various loans.
     * This method should be called after the fields that count the number of various loans have been updated.
     */
    private void updatePropFields() {
        if (this.numActiveLoans > 0 && this.numLoans > 0) {
            this.propActiveLoans = (float) this.numActiveLoans / this.numLoans;
            this.propOverdueLoans = (float) this.numOverdueLoans / this.numActiveLoans;
        }
    }

    /**
     * Updates the fields that calculate the total value of various loans.
     *
     * @param loan The loan to update the fields with.
     */
    private void updateValueFields(Loan loan) {
        totalValueLoaned = totalValueLoaned.add(loan.getValue());
        if (loan.isOverdue()) {
            totalValueOverdue = totalValueOverdue.add(loan.getValue());
        }
        if (loan.isActive()) {
            totalValueActive = totalValueActive.add(loan.getValue());
        }
    }

    /**
     * Updates the fields that calculate the average value of various loans.
     * This method should be called after the fields that calculate the total value of various loans have been updated.
     */
    private void updateAverageFields() {
        if (numActiveLoans > 0) {
            averageActiveValue = totalValueActive.divide(BigDecimal.valueOf(numActiveLoans),
                    2, RoundingMode.HALF_UP);
        }
        if (numOverdueLoans > 0) {
            averageOverdueValue = totalValueOverdue.divide(BigDecimal.valueOf(numOverdueLoans),
                    2, RoundingMode.HALF_UP);
        }
        if (numLoans > 0) {
            averageLoanValue = totalValueLoaned.divide(BigDecimal.valueOf(this.numLoans),
                    2, RoundingMode.HALF_UP);
        }
    }

    /**
     * Updates the fields that calculate the earliest and latest dates of various loans.
     *
     * @param loan The loan to update the fields with.
     */
    private void updateDateFields(Loan loan) {
        if (this.earliestLoanDate == null || loan.getStartDate().before(this.earliestLoanDate)) {
            this.earliestLoanDate = loan.getStartDate();
        }
        if (this.latestLoanDate == null || loan.getStartDate().after(this.latestLoanDate)) {
            this.latestLoanDate = loan.getStartDate();
        }
        if (!loan.isReturned()) {
            if (this.earliestReturnDate == null || loan.getReturnDate().before(this.earliestReturnDate)) {
                this.earliestReturnDate = loan.getReturnDate();
            }
            if (this.latestReturnDate == null || loan.getReturnDate().after(this.latestReturnDate)) {
                this.latestReturnDate = loan.getReturnDate();
            }
        }
    }

    /**
     * Returns an Analytics object that represents the analytics of a LoanRecords object.
     *
     * @param loanList The list of loans to calculate the analytics from.
     * @return The Analytics object that represents the analytics of the LoanRecords object.
     */
    public static Analytics getAnalytics(ObservableList<Loan> loanList) {
        UniqueLoanList uniqueLoanList = new UniqueLoanList();
        uniqueLoanList.setLoans(loanList);
        Analytics analytics = new Analytics();
        for (int i = 0; i < uniqueLoanList.size(); i++) {
            Loan loan = uniqueLoanList.getLoan(i);
            analytics.updateNumFields(loan);
            analytics.updateValueFields(loan);
            analytics.updateDateFields(loan);
        }
        analytics.updatePropFields();
        analytics.updateAverageFields();
        return analytics;
    }

    public int getNumLoans() {
        return numLoans;
    }

    public int getNumOverdueLoans() {
        return numOverdueLoans;
    }

    public int getNumActiveLoans() {
        return numActiveLoans;
    }

    public float getPropOverdueLoans() {
        return propOverdueLoans;
    }

    public float getPropActiveLoans() {
        return propActiveLoans;
    }

    public BigDecimal getTotalValueLoaned() {
        return totalValueLoaned;
    }

    public BigDecimal getTotalValueOverdue() {
        return totalValueOverdue;
    }

    public BigDecimal getTotalValueActive() {
        return totalValueActive;
    }

    public BigDecimal getAverageLoanValue() {
        return averageLoanValue;
    }

    public BigDecimal getAverageOverdueValue() {
        return averageOverdueValue;
    }

    public BigDecimal getAverageActiveValue() {
        return averageActiveValue;
    }

    public Date getEarliestLoanDate() {
        return earliestLoanDate;
    }

    public Date getEarliestReturnDate() {
        return earliestReturnDate;
    }

    public Date getLatestLoanDate() {
        return latestLoanDate;
    }

    public Date getLatestReturnDate() {
        return latestReturnDate;
    }

    @Override
    public String toString() {
        return "Number of loans: " + numLoans + "\n"
                + "Number of overdue loans: " + numOverdueLoans + "\n"
                + "Number of active loans: " + numActiveLoans + "\n"
                + "Proportion of overdue loans: " + propOverdueLoans + "\n"
                + "Proportion of active loans: " + propActiveLoans + "\n"
                + "Total value loaned: " + totalValueLoaned + "\n"
                + "Total value of overdue loans: " + totalValueOverdue + "\n"
                + "Total value of active loans: " + totalValueActive + "\n"
                + "Average loan value: " + averageLoanValue + "\n"
                + "Average value of overdue loans: " + averageOverdueValue + "\n"
                + "Average value of active loans: " + averageActiveValue + "\n"
                + "Earliest loan date: " + earliestLoanDate + "\n"
                + "Earliest return date: " + earliestReturnDate + "\n"
                + "Latest loan date: " + latestLoanDate + "\n"
                + "Latest return date: " + latestReturnDate + "\n";
    }

}
