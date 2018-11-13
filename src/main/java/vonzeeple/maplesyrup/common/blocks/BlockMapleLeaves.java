package vonzeeple.maplesyrup.common.blocks;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.common.Content;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class BlockMapleLeaves extends BlockLeaves {


    public BlockMapleLeaves(){
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
        setCreativeTab(MapleSyrup.creativeTab);
        setUnlocalizedName("maple_leaves");
        setRegistryName("maplesyrup:maple_leaves");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return Blocks.LEAVES.getBlockLayer();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return Blocks.LEAVES.shouldSideBeRendered(state, world, pos, side);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Content.blockMapleSapling);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }

    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | 0;

        if (!((Boolean)state.getValue(DECAYABLE)).booleanValue())
        {
            i |= 4;
        }

        if (((Boolean)state.getValue(CHECK_DECAY)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {CHECK_DECAY, DECAYABLE});
    }
    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return null;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
         return NonNullList.withSize(1, new ItemStack(this, 1));
    }



    // use a custom state mapper which will ignore the DECAY property of leaves
    public StateMapperBase getCustomStateMapper(){
        return new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return new ModelResourceLocation(MapleSyrup.MODID.toLowerCase() + ":maple_leaves", "normal");
            }
        };
    }
}
