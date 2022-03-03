package slogo.parser;

import slogo.command.general.Command;
import slogo.command.value.UserValue;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

public class VariableParser extends AbstractParser {
    private final Pattern pattern = Pattern.compile(":[a-z]+", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean canParse(String token) {
        return pattern.matcher(token).matches();
    }

    @Override
    public Optional<Command> parseToken(String token, Scanner sc) {
        return Optional.of(new UserValue(token.substring(1)));
    }
}
