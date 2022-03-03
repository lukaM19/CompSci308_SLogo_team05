package slogo.parser;

public abstract class AbstractParserTest {
    protected CommandVerifier verifier;
    protected AbstractParser parser;

    // Allows SlogoParserTest to use a SlogoParser instead
    void setParser(AbstractParser parser) {
        this.parser = parser;
        verifier = new CommandVerifier(parser);
    }

    abstract void testCanParse();
}
