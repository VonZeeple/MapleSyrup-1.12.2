package vonzeeple.maplesyrup.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
import scala.util.control.TailCalls;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.client.render.TESRtreetap;
import vonzeeple.maplesyrup.client.render.TESRevaporator;
import vonzeeple.maplesyrup.common.Content;
import vonzeeple.maplesyrup.common.blocks.BlockMapleLeaves;
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

        registerItemBlockRender(Content.itemBlockMapleLog);
        registerItemBlockRender(Content.itemBlockEvaporator);
        registerItemBlockRender(Content.itemBlockMapleSapling);
        registerItemBlockRender(Content.itemBlockTreeTap);
        registerItemLeavesRender(Content.itemBlockMapleLeaves);

        registerItemRender(Content.itemPancakeMix);
        registerItemRender(Content.itemMapleSyrupBottle);
        registerItemRender(Content.itemHydrometer);
        registerItemRender(Content.itemSugarBucket);
        registerItemFoodRender(Content.itemPancakes);

        ClientRegistry.bindTileEntitySpecialRenderer( TileEntityEvaporator.class , new TESRevaporator());
        ClientRegistry.bindTileEntitySpecialRenderer( TileEntityTreeTap.class , new TESRtreetap());

        setCustomStateMapper(Content.blockMapleLeaves);
        setCustomStateMapper(Content.blockFluidMapleSap);
        setCustomStateMapper(Content.blockFluidMapleSyrup);
        setCustomStateMapper(Content.blockFluidBirchSap);
        setCustomStateMapper(Content.blockFluidBirchSyrup);

    }

    private static void setCustomStateMapper(Block block){
        if(! (block instanceof ICustomMappedBlock)){return;}
        ModelLoader.setCustomStateMapper(block, ((ICustomMappedBlock)block).getCustomStateMapper());
    }
    private static void registerItemBlockRender(ItemBlock itemBlock){
        ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation( itemBlock.getRegistryName(), "inventory"));
    }

    private static void registerItemRender(Item item){
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
    }


    private static void registerItemLeavesRender(ItemBlock itemBlock) {
        for (BlockMapleLeaves.EnumColor color :BlockMapleLeaves.EnumColor.values()) {
            ModelLoader.setCustomModelResourceLocation(itemBlock, color.getMeta(), new ModelResourceLocation(itemBlock.getRegistryName(), "color="+color.getName()));
        }
    }

    private static void registerItemFoodRender(Item item) {
        if(!(item instanceof ItemPancakes)){return;}
        String[] names= ((ItemPancakes)item).getSubNames();
        for (int i = 0; i < names.length; i++) {
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(MapleSyrup.MODID+":"+names[i], "inventory"));
        }
    }

}
