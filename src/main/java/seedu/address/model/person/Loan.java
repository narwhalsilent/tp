package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;

import seedu.address.commons.util.DateUtil;
import seedu.address.logic.commands.LinkLoanCommand;

/**
 * Represents a Loan in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Loan implements Comparable<Loan> {

    public static final String DATE_CONSTRAINTS = "Dates should be of the form " + DateUtil.DATE_FORMAT
            + " and the loan start date must be before the return date.";

    public static final String VALUE_CONSTRAINTS = "Loan values must be a positive number.";

    private final int id;
    private final float value;
    private final Date startDate;
    private final Date returnDate;
    private boolean isReturned;
    private Person assignee;

    /**
     * Constructs a {@code Loan} with a given id.
     *
     * @param id         A valid id.
     * @param value      A valid value.
     * @param startDate  A valid start date.
     * @param returnDate A valid return date.
     * @param assignee   A valid assignee.
     */
    public Loan(int id, float value, Date startDate, Date returnDate, Person assignee) {
        requireAllNonNull(id, value, startDate, returnDate, assignee);
        this.id = id;
        this.value = value;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.isReturned = false;
        this.assignee = assignee;
    }

    /**
     * Constructs a {@code Loan} with a given id and return status.
     *
     * @param id         A valid id.
     * @param value      A valid value.
     * @param startDate  A valid start date.
     * @param returnDate A valid return date.
     * @param isReturned A valid return status.
     * @param assignee   A valid assignee.
     */
    public Loan(int id, float value, Date startDate, Date returnDate, boolean isReturned, Person assignee) {
        requireAllNonNull(id, value, startDate, returnDate, isReturned, assignee);
        this.id = id;
        this.value = value;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.isReturned = isReturned;
        this.assignee = assignee;
    }

    /**
     * Returns true if a given float is a valid value.
     */
    public static boolean isValidValue(float value) {
        return value > 0;
    }

    /**
     * Returns true if a given start date and return date are valid.
     */
    public static boolean isValidDates(Date startDate, Date returnDate) {
        return startDate.before(returnDate);
    }

    public int getId() {
        return id;
    }

    public float getValue() {
        return value;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public boolean isActive() {
        return !isReturned;
    }

    public Person getAssignee() {
        return assignee;
    }

    public boolean isAssignedTo(Person person) {
        return assignee.equals(person);
    }

    public int compareTo(Loan other) {
        return this.returnDate.compareTo(other.returnDate);
    }

    /**
     * Returns true if the loan is overdue.
     */
    public boolean isOverdue() {
        // shift return date to the next day
        Date returnDateNextDay = DateUtil.addDay(returnDate, 1);
        return !isReturned && new Date().after(returnDateNextDay);
    }

    /**
     * Marks the loan as returned.
     */
    public void markAsReturned() {
        isReturned = true;
    }

    /**
     * Marks the loan as not returned.
     */
    public void markAsNotReturned() {
        isReturned = false;
    }

    @Override
    public String toString() {
        if (isReturned) {
            return String.format("$%.2f, %s, %s (Returned)", value, DateUtil.format(startDate),
                    DateUtil.format(returnDate));
        } else {
            return String.format("$%.2f, %s, %s", value, DateUtil.format(startDate),
                    DateUtil.format(returnDate));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Loan)) {
            return false;
        }

        Loan otherLoan = (Loan) other;

        return id == otherLoan.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}
