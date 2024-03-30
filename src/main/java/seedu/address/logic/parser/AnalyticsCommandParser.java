package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AnalyticsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AnalyticsCommand object
 */
public class AnalyticsCommandParser implements Parser<AnalyticsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AnalyticsCommand
     * and returns an AnalyticsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AnalyticsCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new AnalyticsCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    MESSAGE_INVALID_COMMAND_FORMAT, pe);
        }
    }

}
