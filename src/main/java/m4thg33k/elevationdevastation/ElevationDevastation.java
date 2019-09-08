package m4thg33k.elevationdevastation;

import m4thg33k.elevationdevastation.calculator.Tester;
import m4thg33k.elevationdevastation.elevation.WorldManager;
import m4thg33k.elevationdevastation.proxy.ClientProxy;
import m4thg33k.elevationdevastation.proxy.IProxy;
import m4thg33k.elevationdevastation.utils.Constants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = Constants.MODID,
        name = Constants.NAME,
        version = Constants.VERSION
)
public class ElevationDevastation {

    public static Logger logger;

    @Mod.Instance
    public static ElevationDevastation INSTANCE = new ElevationDevastation();

    @SidedProxy(
            clientSide = "m4thg33k.elevationdevastation.proxy.ClientProxy",
            serverSide = "m4thg33k.elevationdevastation.proxy.ServerProxy"
    )
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        WorldManager.init();

//        Tester.runTest();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Test message");
    }
}
