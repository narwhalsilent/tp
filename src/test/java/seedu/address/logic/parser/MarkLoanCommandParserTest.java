package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.MarkLoanCommand;



public class MarkLoanCommandParserTest {

    private MarkLoanCommandParser parser = new MarkLoanCommandParser();
    @Test
    public void parse_validArgs_returnsMarkLoanCommand() {
        // no leading and trailing whitespaces
        MarkLoanCommand expectedMarkLoanCommand =
                new MarkLoanCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, "1", expectedMarkLoanCommand);

        // multiple whitespaces between index
        assertParseSuccess(parser, "  1  ", expectedMarkLoanCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // negative index
        assertParseFailure(parser, "-1", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MarkLoanCommand.MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, "-1", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MarkLoanCommand.MESSAGE_USAGE));

        // non-integer index
        assertParseFailure(parser, "a", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MarkLoanCommand.MESSAGE_USAGE));
    }
}
