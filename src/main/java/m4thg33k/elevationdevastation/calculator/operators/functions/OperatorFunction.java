package m4thg33k.elevationdevastation.calculator.operators.functions;

import m4thg33k.elevationdevastation.calculator.EquationOperator;
import m4thg33k.elevationdevastation.calculator.OperatorType;
import m4thg33k.elevationdevastation.calculator.operators.OperatorException;
import m4thg33k.elevationdevastation.calculator.operators.binary.DivisionByZeroException;

public abstract class OperatorFunction extends EquationOperator {

    protected int paramCount;

    public OperatorFunction(int paramCount) {
        super(OperatorType.FUNCTION, 1, true);

        this.paramCount = paramCount;
    }

    public int getParamCount() {
        return this.paramCount;
    }

    @Override
    public float apply(float... values) throws OperatorException, DivisionByZeroException {
        if (values.length != paramCount) {
            throw new OperatorException("Invalid parameter count for function. Expected " + paramCount + " but received " + values.length);
        }

        return doWork(values);
    }

    protected abstract float doWork(float... values);
}
