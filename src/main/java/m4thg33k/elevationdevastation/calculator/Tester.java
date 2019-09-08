package m4thg33k.elevationdevastation.calculator;

import m4thg33k.elevationdevastation.calculator.operators.OperatorException;
import m4thg33k.elevationdevastation.calculator.operators.binary.DivisionByZeroException;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class Tester {
    public static void runTest() {
        String input = "lt(y,70,1.1,2.3)";

        try {
            ParsedFunction function = FunctionParser.parseFunction(input);
            BlockPos pos = new BlockPos(4, 7, 8);
            float value = function.evaluate(null, pos);
            System.out.println(value);
        } catch (FunctionParseException | OperatorException | ParsedFunctionEvaluationException | DivisionByZeroException exception) {
            System.out.println(exception.getMessage());
        }

        int x = 0;
    }
}
