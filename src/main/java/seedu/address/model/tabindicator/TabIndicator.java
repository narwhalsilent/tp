package seedu.address.model.tabindicator;

/**
 * Represents the tab indicator of the dashboard.
 * Determines which tab is currently shown in the GUI.
 */
public class TabIndicator {
    private final boolean isLoansTab;
    private final boolean isAnalyticsTab;
    private final boolean isPersonTab;
    private final boolean isShowAllLoans;
    private final boolean isShowLoaneeInfo;

    /**
     * Default constructor for TabIndicator.
     *
     * @param loans Whether the loans tab is shown.
     * @param analytics Whether the analytics tab is shown.
     * @param person Whether the person tab is shown.
     * @param showAllLoans Whether to show all loans or only active loans.
     * @param showLoaneeInfo Whether to show loanee information.
     */
    public TabIndicator(boolean loans, boolean analytics, boolean person, boolean showAllLoans,
                        boolean showLoaneeInfo) {
        this.isLoansTab = loans;
        this.isAnalyticsTab = analytics;
        this.isPersonTab = person;
        this.isShowAllLoans = showAllLoans;
        this.isShowLoaneeInfo = showLoaneeInfo;
    }

    public boolean getIsLoansTab() {
        return isLoansTab;
    }

    public TabIndicator setIsLoansTab(boolean newIsLoansTab) {
        if (newIsLoansTab) {
            return new TabIndicator(newIsLoansTab, false, false, this.isShowAllLoans, this.isShowLoaneeInfo);
        }
        return new TabIndicator(newIsLoansTab, isAnalyticsTab, isPersonTab, this.isShowAllLoans, this.isShowLoaneeInfo);

    }

    public boolean getIsAnalyticsTab() {
        return isAnalyticsTab;
    }

    public TabIndicator setIsAnalyticsTab(boolean newIsAnalyticsTab) {
        if (newIsAnalyticsTab) {
            return new TabIndicator(false, newIsAnalyticsTab, false, this.isShowAllLoans, this.isShowLoaneeInfo);
        }
        return new TabIndicator(this.isLoansTab, newIsAnalyticsTab, this.isPersonTab, this.isShowAllLoans, this.isShowLoaneeInfo);
    }

    public boolean getIsPersonTab() {
        return isPersonTab;
    }

    public TabIndicator setIsPersonTab(boolean newIsPersonTab) {
        if (newIsPersonTab) {
            return new TabIndicator(false, false, newIsPersonTab, this.isShowAllLoans, this.isShowLoaneeInfo);
        }
        return new TabIndicator(this.isLoansTab, this.isAnalyticsTab, newIsPersonTab, this.isShowAllLoans, this.isShowLoaneeInfo);
    }

    public boolean getIsShowAllLoans() {
        return isShowAllLoans;
    }

    public TabIndicator setIsShowAllLoans(boolean newIsShowAllLoans) {
        return new TabIndicator(this.isLoansTab, this.isAnalyticsTab, this.isPersonTab,
                newIsShowAllLoans, this.isShowLoaneeInfo);
    }

    public boolean getIsShowLoaneeInfo() {
        return isShowLoaneeInfo;
    }

    public TabIndicator setIsShowLoaneeInfo(boolean newIsShowLoaneeInfo) {
        return new TabIndicator(this.isLoansTab, this.isAnalyticsTab, this.isPersonTab,
                this.isShowAllLoans, newIsShowLoaneeInfo);
    }

    public TabIndicator setDualPanelView() {
        return new TabIndicator(true, false, true, this.isShowAllLoans, this.isShowLoaneeInfo);
    }

    /**
     * Returns a string representation of the TabIndicator.
     *
     * @return String representation of the TabIndicator.
     */
    public String toString() {
        return "Loans: " + isLoansTab + "\n"
                + " Analytics: " + isAnalyticsTab + "\n"
                + " Person: " + isPersonTab + "\n"
                + " ShowAllLoans: " + isShowAllLoans + "\n"
                + " ShowLoaneeInfo: " + isShowLoaneeInfo + "\n";
    }


}
