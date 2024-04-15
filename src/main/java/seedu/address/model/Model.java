package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.LinkLoanCommand;
import seedu.address.model.analytics.DashboardData;
import seedu.address.model.person.Analytics;
import seedu.address.model.person.Loan;
import seedu.address.model.person.Person;
import seedu.address.model.tabindicator.TabIndicator;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluates to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * {@code Predicate} that always evaluates to false
     */
    Predicate<Person> PREDICATE_SHOW_NO_PERSONS = unused -> false;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Loan> PREDICATE_SHOW_ALL_LOANS = unused -> true;

    /**
     * {@code Predicate} that evaluates to true if the loan is active
     */
    Predicate<Loan> PREDICATE_SHOW_ALL_ACTIVE_LOANS = loan -> loan.isActive();

    /**
     * {@code Predicate} that always evaluates to false
     */
    Predicate<Loan> PREDICATE_SHOW_NO_LOANS = unused -> false;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns true if a loan with the same identity as {@code loan} exists in the address book.
     */
    boolean hasLoan(Loan loan);

    /**
     * Deletes the given loan.
     * The loan must exist in the address book.
     */
    void deleteLoan(Loan target);

    /**
     * Adds the given loan.
     * {@code loan} must not already exist in the address book.
     */
    void addLoan(Loan loan);

    Loan addLoan(LinkLoanCommand.LinkLoanDescriptor loanDescription, Person assignee);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns an unmodifiable view of the sorted loan list
     */
    ObservableList<Loan> getSortedLoanList();

    /**
     * Updates the filter of the filtered loan list to filter by the given {@code predicate}.
     *
     * @param predicate
     */
    void updateFilteredLoanList(Predicate<Loan> predicate);

    /**
     * Updates the filter of the filtered loan list to filter by the given {@code predicate}.
     * Also updates the preference of whether to show all loans or only active loans.
     *
     * @param predicate
     * @param isShowAllLoans
     */
    void updateFilteredLoanList(Predicate<Loan> predicate, boolean isShowAllLoans);

    /**
     * Sets the tab to the loans tab.
     *
     * @param isLoansTab
     */
    void setIsLoansTab(Boolean isLoansTab);

    /**
     * Sets the tab to the analytics tab.
     *
     * @param isAnalyticsTab
     */
    void setIsAnalyticsTab(Boolean isAnalyticsTab);

    void setToPersonTab();

    void markLoan(Loan loanToMark);

    void generateDashboardData(Analytics analytics);

    void unmarkLoan(Loan loanToUnmark);

    ObjectProperty<DashboardData> getDashboardData();

    void setDualPanel();

    void setIsShowLoaneeInfo(Boolean isShowLoaneeInfo);

    ObjectProperty<TabIndicator> getTabIndicator();
}
