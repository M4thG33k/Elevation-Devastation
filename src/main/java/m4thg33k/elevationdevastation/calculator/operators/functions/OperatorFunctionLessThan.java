package m4thg33k.elevationdevastation.calculator.operators.functions;

public class OperatorFunctionLessThan extends OperatorFunction {
    public OperatorFunctionLessThan() {
        super(4);
    }

    @Override
    protected float doWork(float... values) {
        return values[0] < values[1] ? values[2] : values[3];
    }
}
