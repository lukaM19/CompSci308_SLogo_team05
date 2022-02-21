package slogo.command.general;

/**
 * This class needs to be in the same package as Command to abuse the default access modifier for our testing
 */
public final class ParameterGetter {
    private ParameterGetter() {}

    public static Command getParameter(Command cmd, int index) {
        return cmd.testGetParameter(index);
    }

    public static int getParametersSize(Command cmd) {
        return cmd.getParametersSize();
    }
}
