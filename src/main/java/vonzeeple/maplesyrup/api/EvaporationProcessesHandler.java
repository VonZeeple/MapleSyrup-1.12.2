package vonzeeple.maplesyrup.api;

import net.minecraftforge.fluids.Fluid;

import java.util.HashMap;

public class EvaporationProcessesHandler {

    protected static final HashMap<String, IEvaporationProcess> EvaporationProcesses = new HashMap<>();


    public static void registerProcess(IEvaporationProcess process)
    {
        //should test if valid process
        if(process != null ){
            EvaporationProcesses.put(process.getFluid().getName(), process );
        }
    }


    public static IEvaporationProcess getProcess(Fluid fluid){
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


}
