package m4thg33k.elevationdevastation.calculator.operators;

import m4thg33k.elevationdevastation.calculator.EquationOperator;
import m4thg33k.elevationdevastation.calculator.operators.binary.*;
import m4thg33k.elevationdevastation.calculator.operators.functions.OperatorFunctionGreaterThan;
import m4thg33k.elevationdevastation.calculator.operators.functions.OperatorFunctionLessThan;
import m4thg33k.elevationdevastation.calculator.operators.nullary.OpenParenOperator;

import java.util.HashMap;
import java.util.Map;

public class OperatorLibrary {
    private Map<String, EquationOperator> operatorMap = new HashMap<>();

    private static OperatorLibrary INSTANCE = null;

    private OperatorLibrary() {
        operatorMap.put("(", new OpenParenOperator(Integer.MAX_VALUE, true));
        operatorMap.put("+", new OperatorAddition(2, true));
        operatorMap.put("-", new OperatorSubtraction(2, true));
        operatorMap.put("/", new OperatorDivision(3, true));
        operatorMap.put("*", new OperatorMultiplication(3, true));
        operatorMap.put("^", new OperatorPower(4, false));

        // Functions
        operatorMap.put("lt", new OperatorFunctionLessThan());
        operatorMap.put("gt", new OperatorFunctionGreaterThan());
    }

    private static OperatorLibrary getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OperatorLibrary();
        }

        return INSTANCE;
    }

    public static EquationOperator getOperator(String operatorString) throws OperatorException {
        Map<String, EquationOperator> theMap = OperatorLibrary.getInstance().operatorMap;

        if (!theMap.containsKey(operatorString)) {
            throw new OperatorException("Operator not defined: " + operatorString);
        }
        return theMap.get(operatorString);
    }
}
