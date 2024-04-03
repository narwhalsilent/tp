package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RETURN_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VALUE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditLoanCommand;
import seedu.address.logic.commands.EditLoanCommand.EditLoanDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditLoanCommand object
 */
public class EditLoanCommandParser implements Parser<EditLoanCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditLoanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditLoanCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_VALUE, PREFIX_START_DATE, PREFIX_RETURN_DATE);

        if (arePrefixesEmpty(argMultimap, PREFIX_VALUE, PREFIX_START_DATE, PREFIX_RETURN_DATE)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLoanCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLoanCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_VALUE, PREFIX_START_DATE, PREFIX_RETURN_DATE);

        EditLoanDescriptor editLoanDescriptor = new EditLoanDescriptor();

        if (argMultimap.getValue(PREFIX_VALUE).isPresent()) {
            editLoanDescriptor.setValue(ParserUtil.parseValue(argMultimap.getValue(PREFIX_VALUE).get()));
        }
        if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            editLoanDescriptor.setStartDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_RETURN_DATE).isPresent()) {
            editLoanDescriptor.setReturnDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_RETURN_DATE).get()));
        }

        return new EditLoanCommand(editLoanDescriptor, index);
    }

    /**
     * Returns true if all the prefixes contain empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesEmpty(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }
}
