package m4thg33k.elevationdevastation.calculator.operators.binary;

import m4thg33k.elevationdevastation.calculator.EquationOperator;
import m4thg33k.elevationdevastation.calculator.OperatorType;
import m4thg33k.elevationdevastation.calculator.operators.OperatorException;

public abstract class BinaryOperator extends EquationOperator {

    public BinaryOperator(int precedence, boolean ltr) {
        super(OperatorType.BINARY, precedence, ltr);
    }

    public float apply(float ...values) throws OperatorException, DivisionByZeroException {
        if (values.length != 2){
            throw new OperatorException("Binary operators require 2 inputs");
        }

        return doWork(values[0], values[1]);
    }

    protected abstract float doWork(float first, float second) throws DivisionByZeroException;
}
