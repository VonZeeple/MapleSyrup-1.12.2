package vonzeeple.maplesyrup.api;

import net.minecraftforge.fluids.Fluid;
import java.util.HashMap;

public class EvaporationProcessesHandler {

    protected static final HashMap<String, EvaporationProcess> EvaporationProcesses = new HashMap<>();


    public static void registerProcess(Fluid dilutedFluid, Fluid concentratedFluid, float ratio, int endConcentration, String materialName)
    {
        if(dilutedFluid != null && concentratedFluid != null && ratio > 0.0f){
            EvaporationProcess product=new EvaporationProcess(concentratedFluid.getName(), ratio, endConcentration, materialName);
            EvaporationProcesses.put(dilutedFluid.getName(), product );
        }
    }

    public static void modifyProcess(){}

    public static EvaporationProcess getProcess(Fluid fluid){
        if(canBeEvaporated(fluid) && fluid !=null){
            return EvaporationProcesses.get(fluid.getName());
        }
        return null;
    }
    public static boolean canBeEvaporated(Fluid fluid){
        if(fluid != null)
            return EvaporationProcesses.containsKey(fluid.getName());

        return false;
    }
    public static int getFinalConcentration(Fluid fluid){
        if(canBeEvaporated(fluid) && fluid !=null){
            return EvaporationProcesses.get(fluid.getName()).endConcentration;
        }
        return 0;
    }
    public static String getMaterial(Fluid fluid){
        if(canBeEvaporated(fluid) && fluid !=null){
            return EvaporationProcesses.get(fluid.getName()).materialName;
        }
        return "None";
    }
    public static String getConcentratedFluid(Fluid fluid){

        if(canBeEvaporated(fluid) && fluid !=null){
            return EvaporationProcesses.get(fluid.getName()).getConcentratedFluid();
        }
        return null;
    }

    public static float getRatio(Fluid fluid){

        if(canBeEvaporated(fluid) && fluid !=null){
            return EvaporationProcesses.get(fluid.getName()).getRatio();
        }
        return 0.0f;
    }

}
