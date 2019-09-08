package m4thg33k.elevationdevastation.utils;

import m4thg33k.elevationdevastation.ElevationDevastation;
import m4thg33k.elevationdevastation.elevation.WorldManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void worldTickHandler(TickEvent.WorldTickEvent event) {
        WorldManager.tickWorld(event.world);
    }
}
