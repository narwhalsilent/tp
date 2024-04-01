package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_SHOW_ALL_LOANS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewLoanCommand;
import seedu.address.logic.commands.ViewLoanRelatedCommand;
import seedu.address.logic.commands.ViewLoansCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewLoanCommand object
 */
public class ViewLoanCommandParser implements Parser<ViewLoanRelatedCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewLoanCommand
     * and returns a ViewLoanRelatedCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewLoanRelatedCommand parse(String args) throws ParseException {
        boolean isShowAllLoans = false;
        String modifiedArgs = args;

        // Check if the user wants to view all loans, active or not
        if (modifiedArgs.contains(FLAG_SHOW_ALL_LOANS)) {
            isShowAllLoans = true;
            modifiedArgs = modifiedArgs.replace(FLAG_SHOW_ALL_LOANS, "").trim();
        }

        // Check if the user wants to view all loans, i.e. no index is provided
        if (modifiedArgs.isEmpty()) {
            return new ViewLoansCommand(isShowAllLoans);
        }

        // Check if the user wants to view a specific person's loans
        try {
            Index index = ParserUtil.parseIndex(modifiedArgs);
            return new ViewLoanCommand(index, isShowAllLoans);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewLoanCommand.MESSAGE_USAGE), pe);
        }
    }
}
