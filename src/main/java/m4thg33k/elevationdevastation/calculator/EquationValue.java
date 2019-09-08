package m4thg33k.elevationdevastation.calculator;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class EquationValue extends EquationElement {

    protected float value = 0.0f;

    public float getValue(World world, BlockPos blockPos){
        return this.value;
    }
}
