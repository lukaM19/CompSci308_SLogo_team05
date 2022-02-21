package slogo.parser;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ImpliedArguments.class)
public @interface ImpliedArgument {
    String[] keywords();
    String arg();
    String value();
}
