package vonzeeple.maplesyrup.common.items;

import net.minecraft.item.Item;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.api.IitemHydrometer;

public class ItemHydrometer extends Item implements IitemHydrometer {

    public ItemHydrometer(){
        super();
        setMaxStackSize(1);
        setUnlocalizedName("hydrometer");
        setRegistryName("maplesyrup:hydrometer");
        setCreativeTab(MapleSyrup.creativeTab);
    }
}
