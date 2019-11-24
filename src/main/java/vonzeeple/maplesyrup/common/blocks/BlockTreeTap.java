package vonzeeple.maplesyrup.common.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.common.tileEntities.TileEntityEvaporator;
import vonzeeple.maplesyrup.common.tileEntities.TileEntityTreeTap;
import vonzeeple.maplesyrup.utils.ItemStackUtils;
import vonzeeple.maplesyrup.utils.Logger;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockTreeTap extends Block {


    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool ISFULL= PropertyBool.create("isfull");
    public static final PropertyBool BUCKET= PropertyBool.create("bucket");

    protected static final AxisAlignedBB TAP_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.5D, 0.75D, 0.75D);
    protected static final AxisAlignedBB TAP_WEST_AABB = new AxisAlignedBB(0.5D, 0.0D, 0.25D, 1.0D, 0.75D, 0.75D);
    protected static final AxisAlignedBB TAP_SOUTH_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 0.75D, 0.5D);
    protected static final AxisAlignedBB TAP_NORTH_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.5D, 0.75D, 0.75D, 1.0D);


    public BlockTreeTap() {
        super(Material.WOOD);
        this.setCreativeTab(MapleSyrup.creativeTab);
        setUnlocalizedName("tree_tap");
        setRegistryName("maplesyrup:tree_tap");
        this.setDefaultState(this.getDefaultState().withProperty(ISFULL,false).withProperty(BUCKET,false));
        this.setTickRandomly(true);

    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
        ((TileEntityTreeTap)world.getTileEntity(pos)).activate(world,pos,state,player,hand,side,hitX,hitY,hitZ);

        return true;
    }
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityTreeTap) {
            ItemStackUtils.dropInventoryItems(world, pos, te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
        }

        super.breakBlock(world, pos, state);

    }
    //Give the bounding box according to the direction
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch ((EnumFacing)state.getValue(FACING))
        {
            case NORTH:
                return TAP_NORTH_AABB;
            case SOUTH:
                return TAP_SOUTH_AABB;
            case WEST:
                return TAP_WEST_AABB;
            case EAST:
            default:
                return TAP_EAST_AABB;
        }
    }

    //Can only be placed on a solid wall
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.west()).isSideSolid(worldIn, pos.west(), EnumFacing.EAST) ||
                worldIn.getBlockState(pos.east()).isSideSolid(worldIn, pos.east(), EnumFacing.WEST) ||
                worldIn.getBlockState(pos.north()).isSideSolid(worldIn, pos.north(), EnumFacing.SOUTH) ||
                worldIn.getBlockState(pos.south()).isSideSolid(worldIn, pos.south(), EnumFacing.NORTH);
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        if (facing.getAxis().isHorizontal() && this.canBlockStay(worldIn, pos, facing))
        {
            return this.getDefaultState().withProperty(FACING, facing);
        }
        else
        {
            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
            {
                if (this.canBlockStay(worldIn, pos, enumfacing))
                {
                    return this.getDefaultState().withProperty(FACING, enumfacing);
                }
            }

            return this.getDefaultState();
        }
    }


    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos){
        if (!this.canBlockStay(worldIn, pos, state.getValue(FACING)))
        {
            this.dropBlockAsItem(worldIn, pos, this.getDefaultState(), 0);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (worldIn.isRemote){return;}
        if (!this.canBlockStay(worldIn, pos, state.getValue(FACING)))
        {
            this.dropBlockAsItem(worldIn, pos, this.getDefaultState(), 0);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
        else
        {
            TileEntity entity = worldIn.getTileEntity(pos);
            if(entity instanceof TileEntityTreeTap){
                ((TileEntityTreeTap) entity).update();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand)
    {
        TileEntity entity = world.getTileEntity(pos);
        if(entity instanceof TileEntityTreeTap){
            ((TileEntityTreeTap) entity).displayUpdate(world, stateIn, pos ,rand);
        }
    }


    protected boolean canBlockStay(World worldIn, BlockPos pos, EnumFacing facing)
    {
        return worldIn.getBlockState(pos.offset(facing.getOpposite())).isSideSolid(worldIn, pos.offset(facing.getOpposite()), facing);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(FACING)).getIndex();
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        TileEntity tileentity = worldIn instanceof ChunkCache ? ((ChunkCache)worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityTreeTap) {
            TileEntityTreeTap te = (TileEntityTreeTap) tileentity;
            ItemStack itemstack = te.getBucket();

            if (itemstack != ItemStack.EMPTY) {
                return state.withProperty(BUCKET, true);
            }
            return state.withProperty(BUCKET, false);
        }
        return state.withProperty(BUCKET, false);
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING,ISFULL,BUCKET});
    }


    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityTreeTap();
    }


}
