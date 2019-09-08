package m4thg33k.elevationdevastation.calculator.operators.nullary;

import m4thg33k.elevationdevastation.calculator.EquationOperator;
import m4thg33k.elevationdevastation.calculator.OperatorType;
import m4thg33k.elevationdevastation.calculator.operators.OperatorException;

public class NullaryOperator extends EquationOperator {

    public NullaryOperator(int precedence, boolean ltr){
        super(OperatorType.NULLARY, precedence, ltr);
    }

    public float apply(float ...values) throws OperatorException{
        if (values.length != 0){
            throw new OperatorException("Nullary operators require 0 inputs");
        }

        return 0;
    }
}
