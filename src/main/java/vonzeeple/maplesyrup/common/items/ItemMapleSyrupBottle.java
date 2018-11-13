package vonzeeple.maplesyrup.common.items;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import vonzeeple.maplesyrup.MapleSyrup;

public class ItemMapleSyrupBottle extends Item {

    public ItemMapleSyrupBottle(){
        super();
        setContainerItem(Items.GLASS_BOTTLE);
        setMaxStackSize(16);
        setUnlocalizedName("bottle_maplesyrup");
        setRegistryName("maplesyrup:bottle_maplesyrup");
        setCreativeTab(MapleSyrup.creativeTab);
    }
}
