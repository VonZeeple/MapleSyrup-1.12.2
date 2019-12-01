package vonzeeple.maplesyrup.common.processing;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public class EvaporationProcess implements IProcess<EvaporationProcess> {
    Fluid inputFluid;
    Fluid outputFluid;
    float ratio;
    float endConcentration;
    String materialName;

    ResourceLocation resourceLocation;

    public EvaporationProcess(Fluid inputFluid, Fluid outputFluid, float ratio, String materialName, float endConcentration){
            this.inputFluid=inputFluid;
            this.outputFluid=outputFluid;
            this.ratio=ratio;
            this.materialName=materialName;
            this.endConcentration=endConcentration;
    }
    public float getRatio(){return this.ratio;}
    public Fluid getConcentratedFluid(){return this.outputFluid;}
    public float getBaseConcentration(){
        return (this.endConcentration)*1f/this.ratio;
    }
    public String getMaterialName(){return this.materialName;}

    public Fluid getFluid(){return this.inputFluid;}

    public boolean isValid(){
        if(this.inputFluid == null){return false;}
        if(!FluidRegistry.isFluidRegistered(this.inputFluid)){return false;}
        if(this.outputFluid == null){return false;}
        if(!FluidRegistry.isFluidRegistered(this.outputFluid)){return false;}
        if(this.materialName == null){return false;}
        return true;
    }



    public boolean matches(Object obj) {
        if(this.inputFluid == null){
            return false;
        }
        if(obj instanceof Fluid){
            return ((Fluid) obj).getName().equals(this.inputFluid.getName());
        }
        return false;
    }

    @Override
    public EvaporationProcess setRegistryName(ResourceLocation name) {
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
}
