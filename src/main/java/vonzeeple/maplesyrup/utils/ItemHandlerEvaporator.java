package vonzeeple.maplesyrup.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ItemHandlerEvaporator extends ItemStackHandler {


    //Only accept fuel
    public ItemHandlerEvaporator( int size){
        super(size);
    }

    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate){
        if(!isValidFuel(stack))
            return stack;
        return super.insertItem(slot,stack,simulate);
    }

    public boolean isValidFuel(ItemStack stack){
        return TileEntityFurnace.isItemFuel(stack);
    }
}
