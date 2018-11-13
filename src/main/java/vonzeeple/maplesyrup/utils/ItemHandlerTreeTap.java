package vonzeeple.maplesyrup.utils;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fluids.*;
import net.minecraftforge.items.ItemStackHandler;
import scala.xml.dtd.EMPTY;

import javax.annotation.Nonnull;

public class ItemHandlerTreeTap extends ItemStackHandler {

    //Only accept an empty bucket

    public ItemHandlerTreeTap(int size){
        super(size);
    }


    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate){
        if(!isBucket(stack))
            return stack;
        if (this.getStackInSlot(0).getCount() > 0)
            return stack;
        super.insertItem(slot,new ItemStack(stack.getItem(),1),simulate);
        if(stack.getCount()>1) {
            return new ItemStack(stack.getItem(),stack.getCount()-1);
        }else{
            return ItemStack.EMPTY;
        }
    }


    public boolean gotABucket(){
        return this.getStackInSlot(0) != ItemStack.EMPTY;
    }
    public boolean isBucket(ItemStack stack){
        return stack.getItem()== Items.BUCKET;
    }
}
