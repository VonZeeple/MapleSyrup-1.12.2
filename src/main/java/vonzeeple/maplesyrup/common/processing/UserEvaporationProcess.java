package vonzeeple.maplesyrup.common.processing;



import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import scala.None;
import vonzeeple.maplesyrup.common.processing.EvaporationProcess;
import vonzeeple.maplesyrup.common.processing.IUserProcess;

import java.util.HashMap;
import java.util.Map;


public class UserEvaporationProcess implements IUserProcess {
    private String inputFluid;
    private String outputFluid;
    private float ratio;
    private int endConcentration;
    private String materialName;

    public UserEvaporationProcess(){

    }

    public ResourceLocation getResourceLocation(){
        return null;
    }
    public UserEvaporationProcess(HashMap<String, String> data_in){

        this.outputFluid = data_in.get("outputFluid");
        this.inputFluid = data_in.get("inputFluid");
        this.ratio = Float.valueOf(data_in.get("ratio"));
        this.endConcentration = Integer.valueOf(data_in.get("endConcentration"));
        this.materialName = data_in.get("materialName");
    }

    public EvaporationProcess get_process(){
        return new EvaporationProcess(FluidRegistry.getFluid(this.inputFluid), FluidRegistry.getFluid(this.outputFluid), this.ratio, this.materialName, this.endConcentration);
    }

    public boolean isValid(){
        if(this.inputFluid == null){return false;}
        if(this.outputFluid == null){return false;}
        if(this.materialName == null){return false;}

        return true;
    }
}
