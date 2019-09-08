package m4thg33k.elevationdevastation.calculator.operators.binary;

public class DivisionByZeroException extends Exception {
    public DivisionByZeroException() {
        super("Attempted to divide by a value too close to zero");
    }
}
