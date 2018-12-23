package vonzeeple.maplesyrup.api;

import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.HashMap;

public class EvaporationProcessesHandler {

    protected static final ArrayList<IEvaporationProcess> EvaporationProcesses = new ArrayList<>();


    public static void registerProcess(IEvaporationProcess process)
    {
        //should test if valid process
        if(process != null ){
            EvaporationProcesses.add(process);
        }
    }

    public static IEvaporationProcess getProcess(Fluid startFluid){
        if(startFluid==null)
            return null;
        for(IEvaporationProcess process : EvaporationProcesses)
            if(process.getFluid()== startFluid)
                return process;
        return null;
    }
    public static boolean canBeEvaporated(Fluid fluid){
        if(fluid != null)
            return !(getProcess(fluid)==null);

        return false;
    }


}
