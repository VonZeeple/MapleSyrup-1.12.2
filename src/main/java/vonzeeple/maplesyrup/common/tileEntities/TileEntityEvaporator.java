package vonzeeple.maplesyrup.common.tileEntities;


import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import vonzeeple.maplesyrup.common.blocks.BlockEvaporator;
import vonzeeple.maplesyrup.utils.FluidTankEvaporator;
import vonzeeple.maplesyrup.utils.ItemHandlerEvaporator;


public class TileEntityEvaporator extends TileEntity implements ITickable {

    public static final int SIZE = 1;//number of slots
    private FluidTankEvaporator tank = new FluidTankEvaporator(Fluid.BUCKET_VOLUME*20);
    public int burningTimeLeft = 0;
    public int burningTime = 0;

    // This item handler will hold our inventory slots
    private ItemHandlerEvaporator itemStackHandler = new ItemHandlerEvaporator(SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            TileEntityEvaporator.this.markDirty();
        }
    };


    public FluidStack getFluidStack(){ return this.tank.getFluid();}

    public float getLevelRatio(){return (float)this.tank.getFluidAmount()/(float)this.tank.getCapacity();}

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
        tank.readFromNBT(compound);
        this.burningTimeLeft = compound.getInteger("burningTimeLeft");
        this.burningTime = compound.getInteger("burningTime");
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemStackHandler.serializeNBT());
        tank.writeToNBT(compound);
        compound.setInteger("burningTimeLeft", this.burningTimeLeft);
        compound.setInteger("burningTime", this.burningTime);
        return compound;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }

        return super.hasCapability(capability, facing);
    }



    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T) tank;
        return super.getCapability(capability, facing);
    }

    public float getBurnTimeRatio(){return (this.burningTime==0? 0:this.burningTimeLeft/this.burningTime);}
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

    public boolean isBurning()
    {
        return this.burningTimeLeft > 0;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
        return (oldState.getBlock() != newState.getBlock());
    }

    public String getConcentration(){
        return tank.getConcentration();
    }
    public boolean isEvaporating(){
        return (this.tank.canEvaporate() && this.burningTimeLeft > 0);
    }
    @Override
    public void update() {
        IBlockState state = world.getBlockState(getPos());
        if (!this.world.isRemote) {
            if(burningTimeLeft > 0)
            { this.burningTimeLeft--;
                this.markDirty();
                if(tank.getFluid()!=null)
                     tank.evaporate();
                    IBlockState oldState=this.getWorld().getBlockState(pos);
                     this.getWorld().notifyBlockUpdate(pos, oldState, oldState, 3);
              if(!this.world.getBlockState(getPos()).getValue(BlockEvaporator.active)){
                  this.world.setBlockState(getPos(), state.withProperty(BlockEvaporator.active, true), 8);
                  this.world.notifyBlockUpdate(getPos(), state, state.withProperty(BlockEvaporator.active, true), 8);
                  this.world.notifyNeighborsOfStateChange(getPos(), state.getBlock(), true);
              }
            }
            if(burningTimeLeft ==0 && itemStackHandler.getStackInSlot(0) != ItemStack.EMPTY){
                    ItemStack itemStack = itemStackHandler.getStackInSlot(0);
                    int burntime = itemStack.getItem().getItemBurnTime(itemStack);
                    if (burntime == -1)
                        burntime = TileEntityFurnace.getItemBurnTime(itemStack);
                    if (burntime > 0) {
                        burningTimeLeft = burntime;
                        burningTime = burntime;
                        itemStackHandler.extractItem(0, 1, false);
                    }
                    this.markDirty();
                    return;

            }
            if(burningTimeLeft ==0 && itemStackHandler.getStackInSlot(0) == ItemStack.EMPTY) {
                if(this.world.getBlockState(getPos()).getValue(BlockEvaporator.active)){
                    this.world.setBlockState(getPos(), state.withProperty(BlockEvaporator.active, false), 8);
                    this.world.notifyBlockUpdate(getPos(), state, state.withProperty(BlockEvaporator.active, false), 8);
                    this.world.notifyNeighborsOfStateChange(getPos(), state.getBlock(), true);
                }
            }


        }
    }



}
