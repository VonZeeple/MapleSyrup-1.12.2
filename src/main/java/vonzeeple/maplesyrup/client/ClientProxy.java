package vonzeeple.maplesyrup.client;



import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.client.render.TESRtreetap;
import vonzeeple.maplesyrup.client.render.TESRevaporator;
import vonzeeple.maplesyrup.common.blocks.ICustomMappedBlock;
import vonzeeple.maplesyrup.common.items.ItemPancakes;
import vonzeeple.maplesyrup.common.tileEntities.TileEntityEvaporator;
import vonzeeple.maplesyrup.common.tileEntities.TileEntityTreeTap;


@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy implements IProxy{

    public void preInit(FMLPreInitializationEvent e){}

    public void init(FMLInitializationEvent e) {

        //ResourceLocation leavesColor = new ResourceLocation(MapleSyrup.MODID, "textures/blocks/leaves_maple_color.png");
        //BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        //blockColors.registerBlockColorHandler(new MapleLeavesColor(Minecraft.getMinecraft().getResourceManager()),Content.blockMapleLeaves);

    }

    public void postInit(FMLPostInitializationEvent e){}



    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        OBJLoader.INSTANCE.addDomain(MapleSyrup.MODID);

        registerBlockRender(new ResourceLocation("maplesyrup:maple_log"));
        registerBlockRender(new ResourceLocation("maplesyrup:evaporator"));
        registerBlockRender(new ResourceLocation("maplesyrup:maple_sapling"));
        registerBlockRender(new ResourceLocation("maplesyrup:tree_tap"));
        registerRenderLeaves(new ResourceLocation("maplesyrup:maple_leaves"));

        registerItemRender(new ResourceLocation("maplesyrup:pancakemix"));
        registerItemRender(new ResourceLocation("maplesyrup:bottle_maplesyrup"));
        registerItemRender(new ResourceLocation("maplesyrup:hydrometer"));
        registerItemRender(new ResourceLocation("maplesyrup:sugarbucket"));

        registerRender_food(new ResourceLocation("maplesyrup:pancakes"));

        ClientRegistry.bindTileEntitySpecialRenderer( TileEntityEvaporator.class , new TESRevaporator());
        ClientRegistry.bindTileEntitySpecialRenderer( TileEntityTreeTap.class , new TESRtreetap());

        setCustomStateMapper(new ResourceLocation("maplesyrup:maple_leaves"));
        setCustomStateMapper(new ResourceLocation("maplesyrup:maple_syrup_fluid"));
        setCustomStateMapper(new ResourceLocation("maplesyrup:maple_sap_fluid"));
        setCustomStateMapper(new ResourceLocation("maplesyrup:birch_syrup_fluid"));
        setCustomStateMapper(new ResourceLocation("maplesyrup:birch_sap_fluid"));

    }

    private static void setCustomStateMapper(ResourceLocation location){
        Block block = Block.REGISTRY.getObject(location);
        if(! (block instanceof ICustomMappedBlock)){return;}
        ModelLoader.setCustomStateMapper(block, ((ICustomMappedBlock)block).getCustomStateMapper());
    }

    private static void registerBlockRender(ResourceLocation location){
        Block block = Block.REGISTRY.getObject(location);
        if( block == Blocks.AIR){return;}
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation( Item.getItemFromBlock(block).getRegistryName(), "inventory"));
    }
    private static void registerItemRender(ResourceLocation location){
        Item item = Item.REGISTRY.getObject(location);
        if(item == null){return;}
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
    }

    private static void registerRenderLeaves(ResourceLocation location) {
        Block block = Block.REGISTRY.getObject(location);
        if( block == Blocks.AIR){return;}
        for (int i=0;i<4;i++) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(Item.getItemFromBlock(block).getRegistryName(), "colorindex="+i));
        }
    }

    private static void registerRender_food(ResourceLocation location) {
        Item item = Item.REGISTRY.getObject(location);
        if(!(item instanceof ItemPancakes)){return;}
        String[] names= ((ItemPancakes)item).getSubNames();
        for (int i = 0; i < names.length; i++) {
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(MapleSyrup.MODID+":"+names[i], "inventory"));
        }
    }
}
