package vonzeeple.maplesyrup.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.common.ModConfig;
import vonzeeple.maplesyrup.common.processing.EvaporationProcess;
import vonzeeple.maplesyrup.common.processing.ProcessesHandler;

public class FluidTankEvaporator extends FluidTank {

    protected float materialContent;
    protected String materialName;

    public FluidTankEvaporator(int capacity) {
        super(capacity);
        this.materialContent =0;
        this.materialName="";

    }

    @Override
    public boolean canFillFluidType(FluidStack fluid)
    {
        return (!(ProcessesHandler.find_evaporation_recipe(fluid.getFluid()) == null));
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain){
        int amount=0;
        if(super.drain(maxDrain, false)!=null)
            amount=super.drain(maxDrain, false).amount;

        if(doDrain){
            materialContent *=(1f-(float)amount/(float)getFluidAmount());
        }
        return super.drain(maxDrain, doDrain);
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain){
        int amount=super.drain(resource, false).amount;
        if(doDrain){
            materialContent *=(1f-(float)amount/(float)getFluidAmount());
        }
        return super.drain(resource, doDrain);
    }


    public String getConcentration(){
        if(this.fluid==null){return "Empty";}
        return String.valueOf((this.materialContent/this.getFluidAmount()*100))+"% "+materialName;

    }
    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
        int amount;
        if(resource==null)
            return 0;
        if(ProcessesHandler.find_evaporation_recipe(resource.getFluid()) == null)
            return 0;

        FluidStack fs= this.getFluid();
        EvaporationProcess process=ProcessesHandler.find_evaporation_recipe(resource.getFluid());
        //Dilution
        if(fs!=null){
            if(ProcessesHandler.find_evaporation_recipe(resource.getFluid()).getConcentratedFluid().getName()== fs.getFluid().getName()){

                amount=fillInternal(new FluidStack(fs, resource.amount), false);
                int oldAmount=fs.amount;
                if(doFill){
                    drainInternal(new FluidStack(fs,oldAmount),true);
                    fillInternal(new FluidStack(resource,oldAmount),true);
                    this.materialContent +=(float)amount*process.getBaseConcentration();
                    this.materialName=process.getMaterialName();
                    return fillInternal(resource, true);}else{

                    return fillInternal(new FluidStack(fs, resource.amount), false);}

            }}

        //otherwise
        if(doFill) {
            float matAmount = (float) fill(resource, false) * process.getBaseConcentration();
            this.materialContent += matAmount;
            this.materialName = process.getMaterialName();
        }
        return fillInternal(resource, doFill);
    }

    public boolean canEvaporate() {
        FluidStack fluid=this.getFluid();
        if(fluid==null)
            return false;
        if(ProcessesHandler.find_evaporation_recipe(fluid.getFluid()) == null)
            return false;
        return true;
    }

    //Evaporate: lower the liquid level but keep the same sugar content
    public void evaporate(){
        FluidStack fluid=this.getFluid();
        if(fluid==null)
            return;
        EvaporationProcess process = ProcessesHandler.find_evaporation_recipe(fluid.getFluid());
        if( process == null)
            return;

        FluidStack fluidStack = drainInternal(new FluidStack(this.getFluid(), ModConfig.processes.evaporator_base_speed) ,false);
        if(fluidStack == null){return;}

        //Test if concentration is high enough to switch fluids
        if(materialContent >= process.getEndConcentration()*((float)this.getFluidAmount()-fluidStack.amount)){
            //We first empty the tank
            drainInternal(this.getFluidAmount(),true);
            //We add the right amount of concentrated fluid
            int conAmount = Math.round(materialContent/process.getEndConcentration());
            FluidStack conFluid = new FluidStack(process.getConcentratedFluid(), conAmount);
            fillInternal(conFluid,true);
            return;
        }

        drainInternal(new FluidStack(this.getFluid(), fluidStack.amount) ,true);
    }

    @Override
    public FluidTank readFromNBT(NBTTagCompound nbt)
    {
        if (nbt.hasKey("materialContent"))
        {
            materialContent = nbt.getFloat("materialContent");
        }
        else
        {
            materialContent = 0f;
        }
        if (nbt.hasKey("materialName"))
        {
            materialName = nbt.getString("materialName");
        }
        else
        {
            materialName = "";
        }
        return super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setFloat("materialContent", this.getMaterialContent());
        nbt.setString("materialName", this.materialName);
        return super.writeToNBT(nbt);
    }


    public float getMaterialContent() {
        return materialContent;
    }

}
