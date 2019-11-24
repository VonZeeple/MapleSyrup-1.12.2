package vonzeeple.maplesyrup.common;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.common.blocks.*;
import vonzeeple.maplesyrup.common.items.*;


@ObjectHolder(MapleSyrup.MODID)
public class Content {

    //Blocks
    @ObjectHolder("evaporator")
    public static final BlockEvaporator blockEvaporator = null;
    @ObjectHolder("maple_log")
    public static final BlockMapleLog blockMapleLog = null;
    @ObjectHolder("maple_leaves")
    public static final BlockMapleLeaves blockMapleLeaves = null;
    @ObjectHolder("tree_tap")
    public static final BlockTreeTap blockTreeTap = null;
    @ObjectHolder("maple_sapling")
    public static final BlockMapleSapling blockMapleSapling = null;

    //Fluid blocks
    @ObjectHolder("birch_sap_fluid")
    public static final BlockFluid blockFluidBirchSap = null;
    @ObjectHolder("birch_syrup_fluid")
    public static final BlockFluid blockFluidBirchSyrup = null;
    @ObjectHolder("maple_sap_fluid")
    public static final BlockFluid blockFluidMapleSap = null;
    @ObjectHolder("maple_syrup_fluid")
    public static final BlockFluid blockFluidMapleSyrup = null;

    //Block items
    @ObjectHolder("evaporator")
    public static final ItemBlock itemBlockEvaporator = null;
    @ObjectHolder("maple_log")
    public static final ItemBlock itemBlockMapleLog = null;
    @ObjectHolder("maple_leaves")
    public static final ItemBlock itemBlockMapleLeaves = null;
    @ObjectHolder("tree_tap")
    public static final ItemBlock itemBlockTreeTap = null;
    @ObjectHolder("maple_sapling")
    public static final ItemBlock itemBlockMapleSapling = null;

    //Items
    @ObjectHolder("sugarbucket")
    public static final ItemSugarBucket itemSugarBucket = null;
    @ObjectHolder("pancakes")
    public static final ItemPancakes itemPancakes = null;
    @ObjectHolder("pancakemix")
    public static final ItemPancakeMix itemPancakeMix = null;
    @ObjectHolder("bottle_maplesyrup")
    public static final ItemMapleSyrupBottle itemMapleSyrupBottle = null;
    @ObjectHolder("hydrometer")
    public static final ItemHydrometer itemHydrometer = null;

}
