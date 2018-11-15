package vonzeeple.maplesyrup.common.items;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import vonzeeple.maplesyrup.MapleSyrup;

public class ItemSugarBucket extends Item {
    public ItemSugarBucket(){
        super();
        setContainerItem(Items.BUCKET);
        setMaxStackSize(1);
        setUnlocalizedName("sugarbucket");
        setRegistryName("maplesyrup:sugarbucket");
        setCreativeTab(MapleSyrup.creativeTab);
    }

}
