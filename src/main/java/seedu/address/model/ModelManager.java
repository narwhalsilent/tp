package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.LinkLoanCommand;
import seedu.address.model.person.Loan;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueLoanList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Loan> filteredLoans;
    private final SortedList<Loan> sortedLoans;
    private final BooleanProperty isLoansTab = new SimpleBooleanProperty(false);

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredLoans = new FilteredList<>(this.addressBook.getLoanList());
        sortedLoans = new SortedList<>(filteredLoans, Loan::compareTo);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public boolean hasLoan(Loan loan) {
        requireNonNull(loan);
        return addressBook.hasLoan(loan);
    }

    @Override
    public void deleteLoan(Loan target) {
        addressBook.removeLoan(target);
    }

    @Override
    public void addLoan(Loan loan) {
        addressBook.addLoan(loan);
    }

    @Override
    public void addLoan(LinkLoanCommand.LinkLoanDescriptor loanDescription, Person assignee) {
        addressBook.addLoan(loanDescription, assignee);
    }

    @Override
    public void markLoan(Loan loanToMark) {
        addressBook.markLoan(loanToMark);
    }

    //=========== Filtered Lists Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public ObservableList<Loan> getSortedLoanList() {
        return sortedLoans;
    }

    @Override
    public UniqueLoanList getUniqueLoanList() {
        return addressBook.getUniqueLoanList();
    }

    @Override
    public void updateFilteredLoanList(Predicate<Loan> predicate) {
        requireNonNull(predicate);
        filteredLoans.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && sortedLoans.equals(otherModelManager.sortedLoans);
    }

    @Override
    public void setLoanList(List<Loan> loanList) {
        filteredLoans.clear();
        filteredLoans.setAll(loanList);
    }

    @Override
    public BooleanProperty getIsLoansTab() {
        return this.isLoansTab;
    }

    @Override
    public void setIsLoansTab(Boolean isLoansTab) {
        this.isLoansTab.setValue(isLoansTab);
    }

}
