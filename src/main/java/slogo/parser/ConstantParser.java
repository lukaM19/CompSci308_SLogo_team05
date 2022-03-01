package slogo.parser;

import slogo.command.general.Command;
import slogo.command.value.GenericValue;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConstantParser extends AbstractParser {
    private final Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");

    @Override
    public boolean canParse(String token) {
        return pattern.matcher(token).matches();
    }

    @Override
    public Optional<Command> parseToken(String token, Scanner sc) throws ParserException {
        return Optional.of(new GenericValue(Double.parseDouble(token)));
    }
}
