package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.analytics.DashboardData;
import seedu.address.model.person.Analytics;
import seedu.address.model.person.Loan;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLoan(Loan loan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void setLoanList(List<Loan> loanList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Loan> getSortedLoanList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLoanList(Predicate<Loan> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLoanList(Predicate<Loan> predicate, boolean isShowAllLoans) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public BooleanProperty getIsLoansTab() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIsLoansTab(Boolean isLoansTab) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void markLoan(Loan loanToMark) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unmarkLoan(Loan loanToUnmark) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObjectProperty<DashboardData> getDashboardData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public BooleanProperty getIsPersonTab() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIsPersonTab(Boolean isPersonTab) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDualPanel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public BooleanProperty getLoaneeInfoFlag() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLoaneeInfoFlag(Boolean loaneeInfoFlag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLoan(Loan loan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addLoan(Loan loan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Loan addLoan(LinkLoanCommand.LinkLoanDescriptor loanDescription, Person assignee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIsAnalyticsTab(Boolean isAnalyticsTab) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public BooleanProperty getIsAnalyticsTab() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setToPersonTab() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void generateDashboardData(Analytics analytics) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
