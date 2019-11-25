package vonzeeple.maplesyrup.common.items;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import vonzeeple.maplesyrup.common.blocks.BlockMapleLeaves;

public class ItemMapleLeaves extends ItemBlock {
    private final BlockLeaves leaves;

    public ItemMapleLeaves(BlockLeaves block)
    {
        super(block);
        this.leaves = block;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setRegistryName(block.getRegistryName());
    }

    /**
     * Converts the given ItemStack damage value into a metadata value to be placed in the world when this Item is
     * placed as a Block (mostly used with ItemBlocks).
     */
    public int getMetadata(int meta)
    {
        return meta | 4;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        int color = stack.getMetadata();
        return super.getUnlocalizedName() + "." + BlockMapleLeaves.EnumColor.byMetadata(color).getName();
    }
}
