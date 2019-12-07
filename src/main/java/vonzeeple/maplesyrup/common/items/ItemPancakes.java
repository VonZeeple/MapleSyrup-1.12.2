package vonzeeple.maplesyrup.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import vonzeeple.maplesyrup.MapleSyrup;

public class ItemPancakes extends ItemFood implements IMultiItem{


    private static String[] subNames={"pancakes","pancakes_with_syrup", "pancakes_with_bacon", "candied_bacon","maple_biscuits"};
    private static float[] satModifier={0.5f,0.5f,1f,1f,0.5f};
    private static int[] healAmount={6,7,10,9,2};

    public ItemPancakes(){
        super(0,false);
        setRegistryName("maplesyrup:pancakes");
        setHasSubtypes(true);
        setCreativeTab(MapleSyrup.creativeTab);
    }

    public String[] getSubNames(){
        return subNames;
    }

    @Override
    public void getSubItems( CreativeTabs tab, NonNullList<ItemStack> items) {
        if( !this.isInCreativeTab(tab) )
            return;
        items.add(new ItemStack(this, 1, 0));
        items.add(new ItemStack(this, 1, 1));
        items.add(new ItemStack(this, 1, 2));
        items.add(new ItemStack(this, 1, 3));
        items.add(new ItemStack(this, 1, 4));
    }
    private int getIndex(int value){ return value%subNames.length;}

    @Override
    public float getSaturationModifier(ItemStack stack)
    {
        return  satModifier[getIndex(stack.getMetadata())];
    }

    @Override
    public int getHealAmount(ItemStack stack) { return  healAmount[getIndex(stack.getMetadata())]; }


    @Override
    public String getUnlocalizedName( ItemStack stack) {

        return "item."+subNames[getIndex(stack.getMetadata())];
    }


}
