package m4thg33k.elevationdevastation.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {

    public void preInit(FMLPreInitializationEvent preInitializationEvent);

    public void init(FMLInitializationEvent initializationEvent);

    public void postInit(FMLPostInitializationEvent postInitializationEvent);
}
