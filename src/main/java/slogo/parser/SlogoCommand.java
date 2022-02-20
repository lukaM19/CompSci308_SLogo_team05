package slogo.parser;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SlogoCommand {
    String[] keywords();
    int arguments() default 0;
}
