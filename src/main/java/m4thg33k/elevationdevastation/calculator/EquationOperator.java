package m4thg33k.elevationdevastation.calculator;

import m4thg33k.elevationdevastation.calculator.operators.OperatorException;
import m4thg33k.elevationdevastation.calculator.operators.binary.DivisionByZeroException;

public abstract class EquationOperator extends EquationElement{

    private OperatorType type;
    private int precedence;
    private boolean ltr;

    public EquationOperator(OperatorType type, int precedence, boolean ltr) {
        this.type = type;
        this.precedence = precedence;
        this.ltr = ltr;
    }

    public OperatorType getType() {
        return this.type;
    }

    public int getPrecedence() {
        return this.precedence;
    }

    public boolean isLtr() {
        return this.ltr;
    }

    public abstract float apply(float... values) throws OperatorException, DivisionByZeroException;
}
