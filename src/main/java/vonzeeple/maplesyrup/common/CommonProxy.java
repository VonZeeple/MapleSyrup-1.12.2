package vonzeeple.maplesyrup.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.common.blocks.*;
import vonzeeple.maplesyrup.client.gui.GuiProxy;
import vonzeeple.maplesyrup.common.items.*;
import vonzeeple.maplesyrup.common.processing.*;
import vonzeeple.maplesyrup.common.tileEntities.TileEntityEvaporator;
import vonzeeple.maplesyrup.common.tileEntities.TileEntityTreeTap;

import static vonzeeple.maplesyrup.MapleSyrup.instance;


@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        //Maple Sap
        FluidRegistry.registerFluid(Content.fluidMapleSap);
        FluidRegistry.addBucketForFluid(Content.fluidMapleSap);
        FluidRegistry.registerFluid(Content.fluidMapleSyrup);
        FluidRegistry.addBucketForFluid(Content.fluidMapleSyrup);

        FluidRegistry.registerFluid(Content.fluidBirchSap);
        FluidRegistry.addBucketForFluid(Content.fluidBirchSap);
        FluidRegistry.registerFluid(Content.fluidBirchSyrup);
        FluidRegistry.addBucketForFluid(Content.fluidBirchSyrup);

        ProcessesHandler.get_instance().PreInit();
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiProxy());

        GameRegistry.addSmelting(new ItemStack(Content.itemPancakeMix),new ItemStack(Content.itemPancakes) ,0f);
        GameRegistry.addSmelting(FluidUtil.getFilledBucket(new FluidStack(Content.fluidMapleSyrup, Fluid.BUCKET_VOLUME)),new ItemStack(Content.itemSugarBucket) ,0f);

        registerOres();

        TappableBlockHandler.registerTappableBlock(Content.blockMapleLog.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), new FluidStack(Content.fluidMapleSap,200));
        TappableBlockHandler.registerTappableBlock(Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH), new FluidStack(Content.fluidMapleSap,200));
        TappableBlockHandler.registerTappableBlock(Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK), new FluidStack(FluidRegistry.WATER,200));

        addPattern(MapleSyrup.MODID.toLowerCase()+"_banner","vz_map",new ItemStack(Content.itemMapleSyrupBottle,1));

        ProcessesHandler.get_instance().Init();
    }
    private static void addPattern(String name, String id, ItemStack craftingItem)
    {
        EnumHelper.addEnum(BannerPattern.class, name.toUpperCase(), new Class[] { String.class, String.class, ItemStack.class }, new Object[] { name, id, craftingItem });
    }
    public void postInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public static void createRegistries(RegistryEvent.NewRegistry event){

        buildRegistry(new ResourceLocation(MapleSyrup.MODID+":evaporation_registry"), EvaporationProcess.class);
        buildRegistry(new ResourceLocation(MapleSyrup.MODID+":treeTapping_registry"), TappingProcess.class);

    }

    private static<T extends IForgeRegistryEntry<T> > void buildRegistry(ResourceLocation location, Class<T> entry){
        RegistryBuilder<T> builder = new RegistryBuilder<T>().setType(entry);
        builder.setName(location);
        builder.create();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockEvaporator());
        event.getRegistry().register(new BlockMapleLog());
        event.getRegistry().register(new BlockMapleSapling());
        event.getRegistry().register(new BlockTreeTap());
        event.getRegistry().register(new BlockMapleLeaves());
        GameRegistry.registerTileEntity(TileEntityEvaporator.class, new ResourceLocation("maplesyrup:tileEvaporator"));
        GameRegistry.registerTileEntity(TileEntityTreeTap.class, new ResourceLocation("maplesyrup:tileTreeTap"));
        event.getRegistry().register(new BlockFluid(Content.fluidMapleSap,"maple_sap_fluid"));
        event.getRegistry().register(new BlockFluid(Content.fluidMapleSyrup,"maple_syrup_fluid"));
        }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemPancakes());
        event.getRegistry().register(new ItemPancakeMix());
        event.getRegistry().register(new ItemMapleSyrupBottle());
        event.getRegistry().register(new ItemHydrometer());
        event.getRegistry().register(new ItemSugarBucket());
        event.getRegistry().register(new ItemBlock(Content.blockEvaporator).setRegistryName(Content.blockEvaporator.getRegistryName()));
        event.getRegistry().register(new ItemBlock(Content.blockMapleLog).setRegistryName(Content.blockMapleLog.getRegistryName()));
        event.getRegistry().register(new ItemBlock(Content.blockTreeTap).setRegistryName(Content.blockTreeTap.getRegistryName()));
        event.getRegistry().register(new ItemMapleLeaves(Content.blockMapleLeaves).setRegistryName(Content.blockMapleLeaves.getRegistryName()));
        ItemBlock itemBlockMapleSapling = new ItemBlock(Content.blockMapleSapling) {
            @Override
            public int getItemBurnTime(ItemStack itemStack) { return 100; }
        };
        event.getRegistry().register(itemBlockMapleSapling.setRegistryName(Content.blockMapleSapling.getRegistryName()));
    }

    public static void registerOres(){

        OreDictionary.registerOre("listAllsugar",Items.SUGAR);// From Harvestcraft
        OreDictionary.registerOre("listAllsugar",Content.itemSugarBucket);
        OreDictionary.registerOre("listAllegg",Items.EGG);
        OreDictionary.registerOre("listAllmilk",Items.MILK_BUCKET);
        //For compat with harvestcraft
        OreDictionary.registerOre("cropMaplesyrup",Content.itemMapleSyrupBottle);
    }


}
