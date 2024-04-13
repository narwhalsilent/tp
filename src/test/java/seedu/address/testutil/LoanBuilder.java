package seedu.address.testutil;

import java.math.BigDecimal;
import java.util.Date;

import seedu.address.commons.util.DateUtil;
import seedu.address.model.person.Loan;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Person objects.
 */
public class LoanBuilder {

    public static final int DEFAULT_ID = 9999;
    public static final BigDecimal DEFAULT_VALUE = new BigDecimal("100.00");
    public static final String DEFAULT_START_DATE = "2020-01-01";
    public static final String DEFAULT_RETURN_DATE = "2030-02-01";
    public static final boolean DEFAULT_IS_RETURNED = false;
    public static final Person DEFAULT_ASSIGNEE = new PersonBuilder().build();

    private int id;
    private BigDecimal value;
    private Date startDate;
    private Date returnDate;
    private boolean isReturned;
    private Person assignee;

    /**
     * Creates a {@code LoanBuilder} with the default details.
     */
    public LoanBuilder() {
        id = DEFAULT_ID;
        value = DEFAULT_VALUE;
        isReturned = DEFAULT_IS_RETURNED;
        assignee = DEFAULT_ASSIGNEE;
        try {
            startDate = DateUtil.parse(DEFAULT_START_DATE);
            returnDate = DateUtil.parse(DEFAULT_RETURN_DATE);
        } catch (Exception e) {
            System.out.println("THIS SHOULD NOT HAPPEN");
        }
    }

    /**
     * Initializes the LoanBuilder with the data of {@code loanToCopy}.
     */
    public LoanBuilder(Loan loan) {
        id = loan.getId();
        value = loan.getValue();
        startDate = loan.getStartDate();
        returnDate = loan.getReturnDate();
        isReturned = loan.isReturned();
        assignee = loan.getAssignee();
    }

    /**
     * Sets the {@code id} of the {@code Loan} that we are building.
     */
    public LoanBuilder withId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Parses the {@code value} of the {@code Loan} that we are building.
     */
    public LoanBuilder withValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    /**
     * Sets the {@code startDate} of the {@code Loan} that we are building.
     */
    public LoanBuilder withStartDate(String startDate) {
        try {
            this.startDate = DateUtil.parse(startDate);
        } catch (Exception e) {
            System.out.println("THIS SHOULD NOT HAPPEN");
        }
        return this;
    }

    /**
     * Sets the {@code startDate} of the {@code Loan} that we are building.
     */
    public LoanBuilder withStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * Sets the {@code returnDate} of the {@code Loan} that we are building.
     */
    public LoanBuilder withReturnDate(String returnDate) {
        try {
            this.returnDate = DateUtil.parse(returnDate);
        } catch (Exception e) {
            System.out.println("THIS SHOULD NOT HAPPEN");
        }
        return this;
    }

    /**
     * Sets the {@code returnDate} of the {@code Loan} that we are building.
     */
    public LoanBuilder withReturnDate(Date returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    /**
     * Sets the {@code isReturned} of the {@code Loan} that we are building.
     */
    public LoanBuilder withIsReturned(boolean isReturned) {
        this.isReturned = isReturned;
        return this;
    }

    /**
     * Sets the {@code assignee} of the {@code Loan} that we are building.
     */
    public LoanBuilder withAssignee(Person assignee) {
        this.assignee = assignee;
        return this;
    }

    public Loan build() {
        return new Loan(id, value, startDate, returnDate, isReturned, assignee);
    }

}
