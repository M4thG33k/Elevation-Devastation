package m4thg33k.elevationdevastation.elevation;

import m4thg33k.elevationdevastation.ElevationDevastation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class WorldManager {
    private static Map<Integer, WorldDamageMonitor> theMap = new HashMap<>();

    public static void init() {
        theMap.put(0, new WorldDamageMonitor());
    }

    public static void tickWorld(World world) {
        int dimId = world.provider.getDimension();
        if (theMap.containsKey(dimId)) {
            theMap.get(dimId).handleWorld(world);
        }
    }
}
