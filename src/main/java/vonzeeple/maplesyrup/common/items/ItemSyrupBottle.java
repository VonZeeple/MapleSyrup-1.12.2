package vonzeeple.maplesyrup.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import vonzeeple.maplesyrup.MapleSyrup;

public class ItemSyrupBottle extends Item implements IMultiItem{

    private static String[] subNames={"bottle_maplesyrup","bottle_birchsyrup"};

    public ItemSyrupBottle(){
        super();
        setContainerItem(Items.GLASS_BOTTLE);
        setMaxStackSize(16);
        setRegistryName("maplesyrup:bottle_syrup");
        setHasSubtypes(true);
        setCreativeTab(MapleSyrup.creativeTab);
    }

    public String[] getSubNames(){
        return subNames;
    }


    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if( !this.isInCreativeTab(tab) )
            return;
        items.add(new ItemStack(this, 1, 0));
        items.add(new ItemStack(this, 1, 1));
    }

    private int getIndex(int value){ return value%subNames.length;}


    @Override
    public String getUnlocalizedName( ItemStack stack) {

        return "item."+subNames[getIndex(stack.getMetadata())];
    }
}
