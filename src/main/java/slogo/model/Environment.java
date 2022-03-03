package slogo.model;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private static final Double DEFAULT_VALUE = 0d;

    private final Map<String, Double> userVars = new HashMap<>();

    /**
     * Sets the variable with the specified name to the provided value
     * @param name the name of the variable to set
     * @param value the value to set that variable to
     */
    public void setVariable(String name, Double value) {
        userVars.put(name, value);
    }

    /**
     * Gets the variable with the specified name.
     * Returns 0.0 if the variable has not been defined yet
     * @param name the name of the variable to get
     * @return the value of that variable
     */
    public Double getVariable(String name) {
        return userVars.getOrDefault(name, DEFAULT_VALUE);
    }

    /**
     * Removes all currently set user variables
     */
    public void clearVariables() {
        userVars.clear();
    }
}
