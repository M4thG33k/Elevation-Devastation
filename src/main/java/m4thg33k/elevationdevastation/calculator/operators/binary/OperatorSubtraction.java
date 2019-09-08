package m4thg33k.elevationdevastation.calculator.operators.binary;

public class OperatorSubtraction extends BinaryOperator {
    public OperatorSubtraction(int precedence, boolean ltr) {
        super(precedence, ltr);
    }

    @Override
    protected float doWork(float first, float second) throws DivisionByZeroException {
        return first - second;
    }
}
