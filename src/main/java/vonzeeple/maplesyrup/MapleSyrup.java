package vonzeeple.maplesyrup;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;
import vonzeeple.maplesyrup.common.Content;
import vonzeeple.maplesyrup.common.CommonProxy;
import vonzeeple.maplesyrup.common.processing.ProcessesHandler;

@Mod(modid = MapleSyrup.MODID, name = MapleSyrup.NAME, version = MapleSyrup.VERSION)
public class MapleSyrup
{
    public static final String MODID = "maplesyrup";
    public static final String NAME = "Maple Syrup";
    public static final String VERSION = "1.12.2-0.0";

    public static Logger logger;

    // On instancie la classe du mod
    @Mod.Instance
    public static MapleSyrup instance = new MapleSyrup();

    @SidedProxy(clientSide = "vonzeeple.maplesyrup.client.ClientProxy", serverSide = "vonzeeple.maplesyrup.common.CommonProxy")
    public static CommonProxy proxy;

    //Static initializer
    static {
        FluidRegistry.enableUniversalBucket(); // Must be called before preInit
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        MapleSyrup.proxy.preInit(event);

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MapleSyrup.proxy.init(event);

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        MapleSyrup.proxy.postInit(event);

    }

    public static CreativeTabs creativeTab = new CreativeTabs(MODID)
    {
        @Override
        public ItemStack getTabIconItem()
        {
            return ItemStack.EMPTY;
        }
        @Override
        public ItemStack getIconItemStack()
        {
            return new ItemStack(Content.blockEvaporator,1,0);
        }
    };

}
