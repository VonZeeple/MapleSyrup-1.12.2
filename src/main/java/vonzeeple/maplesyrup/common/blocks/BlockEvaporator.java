package vonzeeple.maplesyrup.common.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.client.particles.ParticleSteam;
import vonzeeple.maplesyrup.common.Content;
import vonzeeple.maplesyrup.common.tileEntities.TileEntityEvaporator;
import vonzeeple.maplesyrup.utils.ItemStackUtils;
import vonzeeple.maplesyrup.utils.Logger;

import javax.annotation.Nullable;
import java.util.Random;


public class BlockEvaporator extends Block {

    public static final PropertyBool active= PropertyBool.create("active");
    public static final int GUI_ID = 1;


    public BlockEvaporator(){
        super(Material.IRON);
        setUnlocalizedName("evaporator");
        setRegistryName("maplesyrup:evaporator");
        setCreativeTab(MapleSyrup.creativeTab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(active, false));
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, active);
    }

    //@Override
    //public Item getItemDropped(IBlockState state, Random rand, int fortune)
    //{
    //    return Item.getItemFromBlock(Content.blockEvaporator);
    //}

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
            return new TileEntityEvaporator();
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

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side,float hitX, float hitY, float hitZ) {


        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof TileEntityEvaporator)) {
            return false;
        }

        if (FluidUtil.interactWithFluidHandler( player,  hand, world,  pos,  side)) {
            world.getTileEntity(pos).markDirty();
            if(((TileEntityEvaporator) world.getTileEntity(pos)).getFluidStack() != null){
                Logger.info("Sap level(2): " + ((TileEntityEvaporator) world.getTileEntity(pos)).getFluidStack().getLocalizedName() + " " + ((TileEntityEvaporator) world.getTileEntity(pos)).getLevelRatio() + "mB");
            }
            IBlockState oldState = world.getBlockState(pos);
            world.notifyBlockUpdate(pos,oldState,state,3);
            return true;
        }



        player.openGui(MapleSyrup.instance, GUI_ID, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(active) ? 1 : 0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(active, meta ==1);
    }
    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos){
        return state.getValue(active)?15:0;
        }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityEvaporator) {
            ItemStackUtils.dropInventoryItems(world, pos, te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
        }

        super.breakBlock(world, pos, state);

    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand)
    {
        TileEntity te = world.getTileEntity(pos);
        if(!(te instanceof TileEntityEvaporator ))
            return;
        if (((TileEntityEvaporator)te).burningTimeLeft > 0)
        {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = (double)pos.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;

            if (rand.nextDouble() < 0.1D)
            {
                world.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
            world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
            world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);

            if(((TileEntityEvaporator) te).isEvaporating()) {
                for (int j = 0; j < 1; ++j) {
                    double xpos = (double) pos.getX() + rand.nextDouble();
                    double ypos = (double) pos.getY() + 1;
                    double zpos = (double) pos.getZ() + rand.nextDouble();
                    ParticleSteam newEffect = new ParticleSteam(world, xpos, ypos, zpos);
                    Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);
                }
            }
        }
    }




}
