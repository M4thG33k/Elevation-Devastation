package m4thg33k.elevationdevastation.calculator.operators.binary;

import m4thg33k.elevationdevastation.calculator.EquationOperator;
import m4thg33k.elevationdevastation.calculator.OperatorType;

public class OperatorAddition extends BinaryOperator {

    public OperatorAddition(int precedence, boolean ltr){
        super(precedence, ltr);
    }

    @Override
    protected float doWork(float first, float second) {
        return first + second;
    }
}
