package vonzeeple.maplesyrup.common.tileEntities;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import vonzeeple.maplesyrup.client.particles.ParticleSap;
import vonzeeple.maplesyrup.common.blocks.BlockTreeTap;
import vonzeeple.maplesyrup.utils.ItemHandlerTreeTap;
import vonzeeple.maplesyrup.common.processing.TappableBlockHandler;

import java.util.Random;

public class TileEntityTreeTap extends TileEntity implements ITickable{
    private FluidTank tank = new FluidTank(Fluid.BUCKET_VOLUME);
    private int tickCounter=0;

    // Inventory slot for bucket
    private ItemHandlerTreeTap itemStackHandler = new ItemHandlerTreeTap(1) {
        @Override
        protected void onContentsChanged(int slot) {
            TileEntityTreeTap.this.updateBlock();
            TileEntityTreeTap.this.markDirty();
        }
    };

    public void update(){
        tickCounter++;
        if(tickCounter>80){
            Random rand = new Random();
            if (rand.nextDouble() < 0.1D) {
                FluidStack fluidstack=this.checkTapping();
                if (fluidstack != null) {

                    //Only server side
                    if (!this.getWorld().isRemote) {

                        if (world.getBlockState(getPos()).getActualState(world,getPos()).getValue(BlockTreeTap.BUCKET) && tank.getFluidAmount() < tank.getCapacity()) {
                            tank.fillInternal(this.checkTapping(), true);
                            this.world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 8);
                            this.markDirty();
                        }
                    }
                    //Only client side
                    if (this.getWorld().isRemote) {

                        this.spawnParticles(this.getWorld(), pos, this.getWorld().getBlockState(pos),fluidstack);
                    }
                    tickCounter = 1;
                }
            }

        }

    }

    public ItemStack getBucket(){
        return itemStackHandler.getStackInSlot(0);
    }

    public boolean isFull(){
        return (this.tank.getFluidAmount()>=this.tank.getCapacity());
    }

    public FluidStack getFluid(){
        return this.tank.getFluid();
    }

    public FluidStack checkTapping(){
        IBlockState state = this.getWorld().getBlockState(pos);

        BlockPos pos2;
        switch (state.getValue(BlockTreeTap.FACING))
        {

            case NORTH:
                pos2=pos.south();
                break;
            case SOUTH:
                pos2=pos.north();
                break;
            case WEST:
                pos2=pos.east();
                break;
            case EAST:
            default:
                pos2=pos.west();
        }

        if (TappableBlockHandler.isTappable(this.getWorld().getBlockState(pos2))){
            return TappableBlockHandler.getTappableSap(this.getWorld().getBlockState(pos2));
        }
        else{
            return null;
        }

    }
    @SideOnly(Side.CLIENT)
    private void spawnParticles(World worldIn, BlockPos pos, IBlockState state,FluidStack fluidstack)
    {
        double x1;
        double z1;
        //Maybe check if the block is TreeTap

        switch (state.getValue(BlockHorizontal.FACING))
        {

            case NORTH:
                x1=0.5D;
                z1=0.8D;
                break;
            case SOUTH:
                x1=0.5D;
                z1=0.2D;
                break;
            case WEST:
                x1=0.8D;
                z1=0.5D;
                break;
            case EAST:
            default:
                x1=0.2D;
                z1=0.5D;
        }

        double xpos=pos.getX()+x1;
        double ypos=pos.getY()+0.57D;
        double zpos=pos.getZ()+z1;
        ResourceLocation resource=fluidstack.getFluid().getStill();
        ParticleSap newEffect = new ParticleSap(worldIn, xpos, ypos, zpos,resource);
        Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);
        //worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 1.0F, 1.0F, false);

    }

    public void updateBlock(){
        IBlockState state = world.getBlockState(getPos());
        this.world.notifyBlockUpdate(getPos(), state, state, 8);
        this.world.notifyNeighborsOfStateChange(getPos(), state.getBlock(), true);
    }

    private void notifyBlockUpdate() {
        final IBlockState state = getWorld().getBlockState(getPos());
        getWorld().notifyBlockUpdate(getPos(), state, state, 3);
    }

    public boolean activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                            EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = player.getHeldItem(hand);
        ItemStack stack = itemStackHandler.getStackInSlot(0);
        if (!heldItem.isEmpty() ){
            player.setHeldItem(hand, this.itemStackHandler.insertItem(0,heldItem,false));
            markDirty();
            return true;
        }
        else if (!stack.isEmpty()) {
            if (!getWorld().isRemote) {
                ItemStack outStack = itemStackHandler.extractItem(0, stack.getCount(), false);
                if(itemStackHandler.isBucket(outStack) && (this.tank.getFluidAmount()>=Fluid.BUCKET_VOLUME)){
                    player.setHeldItem(hand, FluidUtil.getFilledBucket(tank.drain(Fluid.BUCKET_VOLUME,true)));
                    markDirty();
                    return true;
                }
                player.setHeldItem(hand,outStack);
                markDirty();
            }
            return true;
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("bucket")) {
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("bucket"));
        }
        tank.readFromNBT(compound);
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        tank.writeToNBT(compound);
        compound.setTag("bucket", itemStackHandler.serializeNBT());
        return compound;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
        return (oldState.getBlock() != newState.getBlock());
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T) tank;
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, getUpdateTag());
    }


    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
        notifyBlockUpdate();
    }
}
