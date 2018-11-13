package vonzeeple.maplesyrup.api;

import net.minecraftforge.fluids.Fluid;
import java.util.HashMap;

public class EvaporationProcessesHandler {

    static final HashMap<String, EvaporationProduct> EvaporationProcesses = new HashMap<String, EvaporationProduct>();


    public static void registerProcess(Fluid dilutedFluid, Fluid concentratedFluid, float ratio)
    {
        if(dilutedFluid != null && concentratedFluid != null && ratio > 0.0f){
            EvaporationProduct product=new EvaporationProduct(concentratedFluid.getName(), ratio);
            EvaporationProcesses.put(dilutedFluid.getName(), product );
        }
    }
    public static boolean canBeEvaporated(Fluid fluid){
        if(fluid != null)
            return EvaporationProcesses.containsKey(fluid.getName());

        return false;
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

    private static class EvaporationProduct{

        String concentratedFluid;
        float ratio;

        public EvaporationProduct(String fluid, float ratio){
            this.concentratedFluid=fluid;
            this.ratio=ratio;
        }
        public float getRatio(){return this.ratio;}
        public String getConcentratedFluid(){return this.concentratedFluid;}
    }
}
