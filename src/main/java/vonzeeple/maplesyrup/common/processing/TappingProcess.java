package vonzeeple.maplesyrup.common.processing;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;



public class TappingProcess implements IProcess<TappingProcess> {
    public BlockStateEntry blockstate;
    public String fluid;
    public int amount;
    public ResourceLocation resourceLocation;


    public TappingProcess(BlockStateEntry blockstate, String fluid, int amount){
        this.blockstate = blockstate;
        this.fluid = fluid;
        this.amount = amount;
    }


    public FluidStack getFluidStack(){
        return FluidRegistry.getFluidStack(this.fluid, this.amount);
    }

    public boolean matches(Object obj) {
        return blockstate.test(obj);
    }

    @Override
    public TappingProcess setRegistryName(ResourceLocation name) {
        this.resourceLocation = name;
        return this;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return this.resourceLocation;
    }

    @Override
    public Class getRegistryType() {
        return this.getClass();
    }

    @Override
    public boolean isValid(){
        if(blockstate == null){return false;}
        if(fluid == null){return false;}
        return FluidRegistry.getFluid(fluid) != null;
    }
}
