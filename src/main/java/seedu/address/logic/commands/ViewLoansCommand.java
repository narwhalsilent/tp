package seedu.address.logic.commands;

import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACTIVE_LOANS;

public class ViewLoansCommand extends Command {
    public static final String COMMAND_WORD = "viewloans";

    public static final String MESSAGE_SUCCESS = "Listed all loans";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredLoanList(PREDICATE_SHOW_ALL_ACTIVE_LOANS);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
