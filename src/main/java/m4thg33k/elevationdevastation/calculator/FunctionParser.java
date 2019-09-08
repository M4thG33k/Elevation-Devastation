package m4thg33k.elevationdevastation.calculator;

import m4thg33k.elevationdevastation.calculator.operators.OperatorException;
import m4thg33k.elevationdevastation.calculator.operators.OperatorLibrary;
import m4thg33k.elevationdevastation.calculator.operators.binary.DivisionByZeroException;
import m4thg33k.elevationdevastation.calculator.operators.functions.OperatorFunction;
import m4thg33k.elevationdevastation.calculator.operators.nullary.OpenParenOperator;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionParser {

    protected static final Pattern variablePattern = Pattern.compile("^(x|y|z|time)([^a-z].*|$)");
    protected static final Pattern floatPattern = Pattern.compile("^(\\d+\\.?\\d*|\\.\\d+)(?:[^0-9\\.a-z].*$|$)");
    protected static final Pattern spacesPattern = Pattern.compile("^( +).*");
    protected static final Pattern operatorPattern = Pattern.compile("^([^0-9\\. \\(]+).*");

    protected static Stack<EquationElement> output;
    protected static Stack<EquationOperator> operators;

    public static ParsedFunction parseFunction(String function) throws FunctionParseException, OperatorException {
        output = new Stack<>();
        operators = new Stack<>();

        Matcher m;

        while (!function.equals("")) {
            m = spacesPattern.matcher(function);
            if (m.matches()) {
                // starts with some (non-zero) number of spaces; remove them before continuing
                function = function.substring(m.group(1).length());
                continue;
            } else if (function.charAt(0) == ',') {
                // commas should only be used inside functions, but we need to pop operators until the open
                // parenthesis is found - but DON'T pop the parenthesis off!
                moveUntilOpenFound();

                function = function.substring(1);
                continue;
            }

            if (function.charAt(0) == '(') {
                // open parenthesis - always push to operator stack
                operators.push(OperatorLibrary.getOperator("("));
                function = function.substring(1);
            } else if (function.charAt(0) == ')') {
                // close parenthesis - move operators from stack to output until an open parenthesis is found
                FunctionParser.moveUntilOpenFound();
                if (operators.peek() instanceof  OpenParenOperator){
                    operators.pop();
                }
                function = function.substring(1);
//            } else if (variablePattern.matcher(function).matches()) {
//                // we match the pattern for a variable ("x", "y", "z", or "time") - push the variable to the output
//                output.push(new EquationVariable(function.substring(0, 1)));
//                function = function.substring(1);
            } else {
                m = variablePattern.matcher(function);
                if (m.matches()){
                    // found a variable
                    String theVariable = m.group(1);
                    output.push(new EquationVariable(theVariable));
                    function = function.substring(theVariable.length());
                    continue;
                }


                m = floatPattern.matcher(function);

                if (m.matches()) {
                    // found a float (constant value)
                    String theFloat = m.group(1);
                    try {
                        float value = Float.parseFloat(theFloat.trim());
                        // push the value to the output
                        output.push(new EquationConstant(value));

                        function = function.substring(theFloat.length());
                        continue;
                    } catch (NumberFormatException exception) {
                        throw new FunctionParseException("Unable to parse the float: " + theFloat);
                    }
                }

                m = operatorPattern.matcher(function);
                if (m.matches()) {
                    // possible operator found
                    String opString = m.group(1);

                    EquationOperator operator = OperatorLibrary.getOperator(opString);
                    while (FunctionParser.shouldPopOperator(operator)) {
                        moveOperatorToOutput();
                    }

                    operators.push(operator);

                    function = function.substring(opString.length());
                    continue;
                }

                // If this is reached, no valid token was found
                throw new FunctionParseException("No valid token found for function tail: " + function);
            }
        }

        // After all tokens are read, move all leftover operators to the output
        while (operators.size() > 0) {
            EquationOperator theOperator = operators.peek();
            if (theOperator instanceof OpenParenOperator) {
                throw new FunctionParseException("Parenthesis mismatch - missing a closing parenthesis");
            }
            moveOperatorToOutput();
        }

        return new ParsedFunction(new ArrayList<EquationElement>(output));
    }

    protected static boolean shouldPopOperator(EquationOperator newestOperator) {
        if (operators.size() == 0 || newestOperator.getType() == OperatorType.FUNCTION) {
            return false;
        }

        EquationOperator topOperator = operators.peek();
        if (topOperator instanceof OpenParenOperator) {
            return false;
        } else {

            return topOperator.getType() == OperatorType.FUNCTION ||
                    topOperator.getPrecedence() > newestOperator.getPrecedence() ||
                    (
                            topOperator.getPrecedence() == newestOperator.getPrecedence() && topOperator.isLtr()
                    );
        }
    }

    protected static void moveUntilOpenFound() throws FunctionParseException {
        while (operators.size() > 0) {
            EquationOperator top = operators.peek();
            if (top instanceof OpenParenOperator) {
                // found the open parenthesis
                return;
            } else {
                // not the open parenthesis - move the operator
                moveOperatorToOutput();
            }
        }

        // if we run out of operators, we have a parenthesis mismatch!
        throw new FunctionParseException("Parenthesis mismatch - missing an open parenthesis");
    }

    protected static void moveOperatorToOutput() throws FunctionParseException {
        // todo FIX INCORRECT INPUTS BEING PASSED TO OPERATORS INSIDE OF FUNCTIONS::q
        if (operators.size() == 0) {
            throw new FunctionParseException("Operator stack empty");
        }

        EquationOperator theOperator = operators.pop();
        if (theOperator instanceof OpenParenOperator) {
            return;
        }

        try {
            switch (theOperator.getType()) {
                case NULLARY:
                    // no inputs - shouldn't be used
                    break;
                case UNARY:
                    // one input
                    EquationElement value = output.pop();
                    if (value instanceof EquationConstant) {
                        // We can apply the operator right now and push it out
                        float newValue = theOperator.apply(((EquationConstant) value).getValue(null, null));
                        EquationElement newElement = new EquationConstant(newValue);
                        output.push(newElement);
                    } else {
                        // we have to wait for more input; put the value back and push the operator
                        output.push(value);
                        output.push(theOperator);
                    }
                    break;
                case BINARY:
                    // two inputs
                    EquationElement lastValue = output.pop();
                    EquationElement nextToLastValue = output.pop();

                    if (lastValue instanceof EquationConstant && nextToLastValue instanceof EquationConstant) {
                        // we can apply the operator to the two values and push it out
                        float newValue = theOperator.apply(
                                ((EquationConstant) nextToLastValue).getValue(null, null),
                                ((EquationConstant) lastValue).getValue(null, null)
                        );
                        EquationConstant newElement = new EquationConstant(newValue);
                        output.push(newElement);
                    } else {
                        // we have to wait for more input; put the values back in the same order and push the operator
                        output.push(nextToLastValue);
                        output.push(lastValue);
                        output.push(theOperator);
                    }
                    break;
                case FUNCTION:
                    OperatorFunction function = (OperatorFunction) theOperator;
                    int paramCount = function.getParamCount();
                    EquationElement[] elements = new EquationElement[paramCount];
                    boolean allConstant = true;
                    for (int i = paramCount - 1; i >= 0; i--) {
                        elements[i] = output.pop();
                        if (!(elements[i] instanceof EquationConstant)) {
                            allConstant = false;
                        }
                    }
                    if (allConstant) {
                        // we can apply the function to the inputs and push the result instead
                        float[] values = new float[paramCount];
                        for (int i = 0; i < paramCount; i++) {
                            values[i] = ((EquationConstant) elements[i]).getValue(null, null);
                        }
                        float newValue = function.apply(values);
                        output.push(new EquationConstant(newValue));
                    } else {
                        // we need to put everything back and wait to evaluate
                        for (EquationElement element :
                                elements) {
                            output.push(element);
                        }
                        output.push(theOperator);
                    }
                    break;
            }
        } catch (OperatorException exception) {
            throw new FunctionParseException("Could not condense constant functions. Error received: " + exception.getMessage());
        } catch (DivisionByZeroException exception) {
            throw new FunctionParseException("Could not condense constant functions. Attempted to divide by a value close to zero!");
        }
    }
}
