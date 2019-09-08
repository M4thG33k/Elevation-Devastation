package m4thg33k.elevationdevastation.calculator;

import m4thg33k.elevationdevastation.calculator.operators.OperatorException;
import m4thg33k.elevationdevastation.calculator.operators.binary.DivisionByZeroException;
import m4thg33k.elevationdevastation.calculator.operators.functions.OperatorFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Stack;

public class ParsedFunction {

    protected List<EquationElement> elements;

    public ParsedFunction(List<EquationElement> elements) {
        this.elements = elements;
    }

    public float evaluate(World world, BlockPos pos) throws OperatorException, ParsedFunctionEvaluationException, DivisionByZeroException {
        Stack<Float> stack = new Stack<>();

        for (EquationElement element :
                elements) {
            if (element instanceof EquationValue) {
                stack.push(((EquationValue) element).getValue(world, pos));
                continue;
            }

            if (!(element instanceof EquationOperator)) {
                continue;
            }

            EquationOperator operator = (EquationOperator) element;
            float newValue;

            switch (operator.getType()) {
                case UNARY:
                    if (stack.size() < 1) {
                        throw new OperatorException("Not enough inputs for unary function");
                    }

                    float oldValue = stack.pop();
                    newValue = operator.apply(oldValue);
                    stack.push(newValue);
                    break;
                case BINARY:
                    if (stack.size() < 2) {
                        throw new OperatorException("Not enough inputs for binary function");
                    }

                    float secondValue = stack.pop();
                    float firstValue = stack.pop();
                    newValue = operator.apply(firstValue, secondValue);

                    stack.push(newValue);
                    break;
                case FUNCTION:
                    OperatorFunction function = (OperatorFunction) operator;
                    int paramCount = function.getParamCount();

                    if (stack.size() < paramCount) {
                        throw new OperatorException("Not enough inputs for function. Looking for " + paramCount + " but received " + stack.size());
                    }

                    float[] values = new float[paramCount];
                    for (int i = paramCount - 1; i >= 0; i--) {
                        values[i] = stack.pop();
                    }

                    newValue = function.apply(values);

                    stack.push(newValue);
                    break;
            }
        }

        if (stack.size() != 1) {
            throw new ParsedFunctionEvaluationException("Failed to evaluate function - invalid number of arguments remaining: " + stack.size());
        }

        return stack.pop();
    }
}
