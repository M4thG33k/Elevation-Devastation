package m4thg33k.elevationdevastation.calculator.operators.binary;

public class OperatorDivision extends BinaryOperator {

    public OperatorDivision(int precedence, boolean ltr) {
        super(precedence, ltr);
    }

    @Override
    protected float doWork(float first, float second) throws DivisionByZeroException {
        if (Math.abs(second) < 1e-10) {
            throw new DivisionByZeroException();
        }

        return first / second;
    }
}
