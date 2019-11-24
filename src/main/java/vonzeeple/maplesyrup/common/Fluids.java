package vonzeeple.maplesyrup.common;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import vonzeeple.maplesyrup.MapleSyrup;

public class Fluids {

    public static final Fluid fluidMapleSap = new Fluid("maple_sap_fluid",new ResourceLocation(MapleSyrup.MODID+":blocks/maple_sap_still"),new ResourceLocation(MapleSyrup.MODID+":blocks/maple_sap_flow"));
    public static final Fluid fluidMapleSyrup = new Fluid("maple_syrup_fluid",new ResourceLocation(MapleSyrup.MODID+":blocks/maple_syrup_still"),new ResourceLocation(MapleSyrup.MODID+":blocks/maple_syrup_flow"));

    public static final Fluid fluidBirchSap = new Fluid("birch_sap_fluid",new ResourceLocation(MapleSyrup.MODID+":blocks/maple_sap_still"),new ResourceLocation(MapleSyrup.MODID+":blocks/maple_sap_flow"));
    public static final Fluid fluidBirchSyrup = new Fluid("birch_syrup_fluid",new ResourceLocation(MapleSyrup.MODID+":blocks/maple_syrup_still"),new ResourceLocation(MapleSyrup.MODID+":blocks/maple_syrup_flow"));

}
