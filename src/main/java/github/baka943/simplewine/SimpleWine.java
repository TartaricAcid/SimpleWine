package github.baka943.simplewine;

import github.baka943.simplewine.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = SimpleWine.MODID, name = SimpleWine.NAME, version = SimpleWine.VERSION, acceptedMinecraftVersions = "1.12.2", dependencies = SimpleWine.DEPEND)
public class SimpleWine {
    public static final String MODID = "simple_wine";
    public static final String VERSION = "1.0.0";
    public static final String  NAME = "Simple Mod";
    public static final String DEPEND = "before:guideapi";

    @SidedProxy(clientSide = "github.baka943.simplewine.proxy.ClientProxy", serverSide = "github.baka943.simplewine.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static SimpleWine instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        logger = event.getModLog();
        System.out.println("Simple Wine Mod Is PreInit");
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        System.out.println("Simple Wine Mod Is Init");
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        System.out.println("Simple Wine Mod Is PostInit");
        proxy.postInit(event);
    }
}
