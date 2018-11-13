package vonzeeple.maplesyrup.client;



import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.client.render.MapleLeavesColor;
import vonzeeple.maplesyrup.client.render.TESRtreetap;
import vonzeeple.maplesyrup.common.CommonProxy;
import vonzeeple.maplesyrup.common.Content;
import vonzeeple.maplesyrup.client.render.TESRevaporator;
import vonzeeple.maplesyrup.common.items.ItemPancakes;
import vonzeeple.maplesyrup.common.tileEntities.TileEntityEvaporator;
import vonzeeple.maplesyrup.common.tileEntities.TileEntityTreeTap;


@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        OBJLoader.INSTANCE.addDomain(MapleSyrup.MODID);

        registerRender(Item.getItemFromBlock(Content.blockMapleLog));
        registerRender(Item.getItemFromBlock(Content.blockEvaporator));
        registerRender(Item.getItemFromBlock(Content.blockMapleSapling));
        registerRender(Item.getItemFromBlock(Content.blockMapleLeaves));
        registerRender(Item.getItemFromBlock(Content.blockTreeTap));
        registerRender_food(Content.itemPancakes);
        registerRender(Content.itemPancakeMix);
        registerRender(Content.itemMapleSyrupBottle);
        registerRender(Content.itemHydrometer);

        ClientRegistry.bindTileEntitySpecialRenderer( TileEntityEvaporator.class , new TESRevaporator());
        ClientRegistry.bindTileEntitySpecialRenderer( TileEntityTreeTap.class , new TESRtreetap());

        ModelLoader.setCustomStateMapper(Content.blockMapleLeaves, Content.blockMapleLeaves.getCustomStateMapper());
        ModelLoader.setCustomStateMapper(Content.blockFluidMapleSap,Content.blockFluidMapleSap.getCustomStateMapper());
        ModelLoader.setCustomStateMapper(Content.blockFluidMapleSyrup, Content.blockFluidMapleSyrup.getCustomStateMapper());


    }

    public void init(FMLInitializationEvent e) {
        super.init(e);

        ResourceLocation leavesColor = new ResourceLocation(MapleSyrup.MODID, "textures/blocks/leaves_maple_color.png");
        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        blockColors.registerBlockColorHandler(new MapleLeavesColor(Minecraft.getMinecraft().getResourceManager()),Content.blockMapleLeaves);

    }

    public static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
    }
    public static void registerRender_food(ItemPancakes item) {
        String[] names=item.getSubNames();
        for (int i = 0; i < names.length; i++) {
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(MapleSyrup.MODID+":"+names[i], "inventory"));
        }
    }
}
