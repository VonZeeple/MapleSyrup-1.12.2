package vonzeeple.maplesyrup.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import vonzeeple.maplesyrup.api.EvaporationProcessesHandler;
import vonzeeple.maplesyrup.api.IEvaporationProcess;

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
        return EvaporationProcessesHandler.canBeEvaporated(fluid.getFluid());
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
        if(this.fluid==null){return "None";}
        return String.valueOf((int)(materialContent/this.getFluidAmount()*100))+"% "+materialName;

    }
    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
        int amount;
        if(resource==null)
            return 0;
        if(!EvaporationProcessesHandler.canBeEvaporated(resource.getFluid()))
            return 0;

        FluidStack fs= this.getFluid();
        IEvaporationProcess process=EvaporationProcessesHandler.getProcess(resource.getFluid());
        //Dilution
        if(fs!=null){
            if(EvaporationProcessesHandler.getProcess(resource.getFluid()).getConcentratedFluid().getName()== fs.getFluid().getName()){

                amount=fillInternal(new FluidStack(fs, resource.amount), false);
                int oldAmount=fs.amount;
                if(doFill){
                    drainInternal(new FluidStack(fs,oldAmount),true);
                    fillInternal(new FluidStack(resource,oldAmount),true);
                    this.materialContent +=(float)amount/1000f*process.getBaseConcentration();
                    this.materialName=process.getMaterialName();
                    return fillInternal(resource, true);}else{

                    return fillInternal(new FluidStack(fs, resource.amount), false);}

            }}

        //otherwise
        if(doFill)
            this.materialContent +=(float)fill(resource, false)/1000f*process.getBaseConcentration();
            this.materialName=process.getMaterialName();
        return fillInternal(resource, doFill);
    }

    public boolean canEvaporate() {
        FluidStack fluid=this.getFluid();
        if(fluid==null)
            return false;
        if(!EvaporationProcessesHandler.canBeEvaporated(fluid.getFluid()))
            return false;
        return true;
    }

    //Evaporate: lower the liquid level but keep the same sugar content
    public void evaporate(){
        FluidStack fluid=this.getFluid();
        if(fluid==null)
            return;
        if(!EvaporationProcessesHandler.canBeEvaporated(fluid.getFluid()))
            return;

        //Test if the content can be transformed in the concentrated version
        IEvaporationProcess process=EvaporationProcessesHandler.getProcess(fluid.getFluid());
        if(materialContent >=process.getRatio()*process.getBaseConcentration()*(float)this.getFluidAmount()/1000f){
            int fluidamount=getFluidAmount();
            String conFluidname=EvaporationProcessesHandler.getProcess(fluid.getFluid()).getConcentratedFluid().getName();
            FluidStack conFluid= FluidRegistry.getFluidStack(conFluidname, fluidamount);
            drainInternal(new FluidStack(fluid, fluidamount) ,true);
            fillInternal(conFluid,true);
            fluid=this.getFluid();
            return;
        }
        drainInternal(new FluidStack(this.getFluid(), 20) ,true);
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
