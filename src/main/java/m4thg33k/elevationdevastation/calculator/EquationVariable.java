package m4thg33k.elevationdevastation.calculator;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EquationVariable extends EquationValue {

    protected String variable = "";

    public EquationVariable(String variable) {
        switch (variable) {
            case "x":
            case "y":
            case "z":
            case "time":
                this.variable = variable;
                break;
        }
    }

    @Override
    public float getValue(World world, BlockPos blockPos) {
        switch (this.variable) {
            case "x":
                return blockPos.getX();
            case "y":
                return blockPos.getY();
            case "z":
                return blockPos.getZ();
            case "time":
                return world.getWorldTime();
            default:
                return 0.0f;
        }
    }
}
