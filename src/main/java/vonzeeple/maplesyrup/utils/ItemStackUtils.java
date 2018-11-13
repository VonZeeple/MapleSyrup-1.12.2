package vonzeeple.maplesyrup.utils;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import java.util.Random;

public class ItemStackUtils {
    //Version of dropInventoryItems that support IItemHandler
    public static void dropInventoryItems(World world, BlockPos pos, IItemHandler itemHandler) {
        for (int i = 0; i < itemHandler.getSlots(); ++i) {
            ItemStack itemstack = itemHandler.getStackInSlot(i);

            if (!itemstack.isEmpty()) {
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
            }
        }
    }
}
