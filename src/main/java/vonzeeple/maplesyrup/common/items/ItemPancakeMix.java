package vonzeeple.maplesyrup.common.items;

import net.minecraft.item.Item;
import vonzeeple.maplesyrup.MapleSyrup;

public class ItemPancakeMix extends Item {

    public ItemPancakeMix(){
        super();
        setUnlocalizedName("pancakemix");
        setRegistryName("maplesyrup:pancakemix");
        setCreativeTab(MapleSyrup.creativeTab);
    }
}
