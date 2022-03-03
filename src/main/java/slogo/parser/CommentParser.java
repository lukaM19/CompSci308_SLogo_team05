package slogo.parser;

import slogo.command.general.Command;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CommentParser extends AbstractParser {
    private final Pattern pattern = Pattern.compile("^#.*");

    @Override
    public boolean canParse(String token) {
        return pattern.matcher(token).matches();
    }

    @Override
    public Optional<Command> parseToken(String token, Scanner sc) {
        if(sc.hasNext()) {
            sc.nextLine(); // Discard the rest of the line, since it's all a comment
        }
        return Optional.empty();
    }
}
