package m4thg33k.elevationdevastation.elevation;

import m4thg33k.elevationdevastation.ElevationDevastation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;

public class WorldDamageMonitor {

    private int tick = 0;
    private int interval = 40;

    private DamageFunction damageFunction;

    public WorldDamageMonitor() {
        damageFunction = new DamageFunction("gt(time, 9000, lt(y, 40, (40- y) / 10, gt(y, 80, (y - 80) / 10, 0)), 0)");
    }

    public void handleWorld(World world) {
        if (!damageFunction.isValid()) {
            return;
        }
        tick = (tick + 1) % interval;
        if (tick == 0) {
            for (EntityPlayer player :
                    world.playerEntities) {
                this.handlePlayer(world, player);
            }
        }
    }

    private void handlePlayer(World world, EntityPlayer player) {
        ElevationDevastation.logger.info(
                "Player {} at {}",
                player.getName(),
                player.getPosition().toString()
        );
        float damage = damageFunction.apply(new Tuple<>(world, player.getPosition()));
        if (damage > 0) {
            ElevationDevastation.logger.info("Would deal {} damage", damage);
        }
    }
}
