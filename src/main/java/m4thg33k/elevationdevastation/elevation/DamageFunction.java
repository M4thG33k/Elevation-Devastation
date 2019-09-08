package m4thg33k.elevationdevastation.elevation;

import m4thg33k.elevationdevastation.ElevationDevastation;
import m4thg33k.elevationdevastation.calculator.FunctionParseException;
import m4thg33k.elevationdevastation.calculator.FunctionParser;
import m4thg33k.elevationdevastation.calculator.ParsedFunction;
import m4thg33k.elevationdevastation.calculator.operators.OperatorException;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Function;

public class DamageFunction implements Function<Tuple<World, BlockPos>, Float> {

    private ParsedFunction parsedFunction;

    public DamageFunction(String function) {
        try {
            parsedFunction = FunctionParser.parseFunction(function);
        } catch (FunctionParseException | OperatorException exception) {
            ElevationDevastation.logger.warn(exception.getMessage());
            parsedFunction = null;
        }
    }

    public boolean isValid() {
        return parsedFunction != null;
    }

    @Override
    public Float apply(Tuple<World, BlockPos> data) {
        try {
            if (isValid()) {
                return parsedFunction.evaluate(data.getFirst(), data.getSecond());
            }
        } catch (Exception e){
            return 0f;
        }

        return 0f;
    }
}
