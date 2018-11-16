package vonzeeple.maplesyrup.common;

import com.sun.istack.internal.NotNull;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.common.blocks.*;
import vonzeeple.maplesyrup.common.items.*;

import javax.annotation.Nullable;


//Inject values from registry
public class Content {

    @ObjectHolder(MapleSyrup.MODID+":"+"evaporator")//format modid:name
    public static final BlockEvaporator blockEvaporator = null;

    @ObjectHolder(MapleSyrup.MODID+":"+"maple_log")//format modid:name
    public static final BlockMapleLog blockMapleLog = null;

    @ObjectHolder(MapleSyrup.MODID+":"+"maple_leaves")//format modid:name
    public static final BlockMapleLeaves blockMapleLeaves = null;

    @ObjectHolder(MapleSyrup.MODID+":"+"tree_tap")//format modid:name
    public static final BlockTreeTap blockTreeTap = null;

    @ObjectHolder(MapleSyrup.MODID+":"+"maple_sapling")//format modid:name
    public static final BlockMapleSapling blockMapleSapling = null;

    @ObjectHolder(MapleSyrup.MODID+":"+"maple_sap_fluid")//format modid:name
    public static final BlockFluid blockFluidMapleSap = null;

    @ObjectHolder(MapleSyrup.MODID+":"+"maple_syrup_fluid")//format modid:name
    public static final BlockFluid blockFluidMapleSyrup = null;
    //public static final BlockFluidMapleSyrup blockFluidMapleSyrup = null;

    @ObjectHolder(MapleSyrup.MODID+":"+"sugarbucket")//format modid:name
    public static final ItemSugarBucket itemSugarBucket = null;
    //public static final BlockFluidMapleSyrup blockFluidMapleSyrup = null;

    @ObjectHolder(MapleSyrup.MODID+":"+"pancakes")//format modid:name
    public static final ItemPancakes itemPancakes = null;
    //public static final BlockFluidMapleSyrup blockFluidMapleSyrup = null;

    @ObjectHolder(MapleSyrup.MODID+":"+"pancakemix")//format modid:name
    public static final ItemPancakeMix itemPancakeMix = null;
    //public static final BlockFluidMapleSyrup blockFluidMapleSyrup = null;

    @ObjectHolder(MapleSyrup.MODID+":"+"bottle_maplesyrup")//format modid:name
    public static final ItemMapleSyrupBottle itemMapleSyrupBottle = null;

    @ObjectHolder(MapleSyrup.MODID+":"+"hydrometer")//format modid:name
    public static final ItemHydrometer itemHydrometer = null;


    public static final Fluid fluidMapleSap = new Fluid("maple_sap_fluid",new ResourceLocation(MapleSyrup.MODID+":blocks/maplesap_still"),new ResourceLocation(MapleSyrup.MODID+":blocks/maplesap_flow"));
    public static final Fluid fluidMapleSyrup = new Fluid("maple_syrup_fluid",new ResourceLocation(MapleSyrup.MODID+":blocks/maplesyrup_still"),new ResourceLocation(MapleSyrup.MODID+":blocks/maplesyrup_flow"));

    public static final Fluid fluidBirchSap = new Fluid("birch_sap_fluid",new ResourceLocation(MapleSyrup.MODID+":blocks/birchsap_still"),new ResourceLocation(MapleSyrup.MODID+":blocks/birchsap_flow"));
    public static final Fluid fluidBirchSyrup = new Fluid("birch_syrup_fluid",new ResourceLocation(MapleSyrup.MODID+":blocks/birchsyrup_still"),new ResourceLocation(MapleSyrup.MODID+":blocks/birchsyrup_flow"));


}
