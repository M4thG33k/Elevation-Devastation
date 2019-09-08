package m4thg33k.elevationdevastation.calculator.operators.binary;

public class OperatorPower extends BinaryOperator {
    public OperatorPower(int precedence, boolean ltr) {
        super(precedence, ltr);
    }

    @Override
    protected float doWork(float first, float second) throws DivisionByZeroException {
        return (float)Math.pow(first, second);
    }
}
