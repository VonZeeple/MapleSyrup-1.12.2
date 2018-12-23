package vonzeeple.maplesyrup.utils;

import net.minecraftforge.fluids.Fluid;
import vonzeeple.maplesyrup.api.IEvaporationProcess;

public class EvaporationProcess implements IEvaporationProcess {
    Fluid startFluid;
    Fluid endFluid;
    float ratio;
    int endConcentration;
    String materialName;


    public EvaporationProcess(Fluid startFluid, Fluid endFluid, float ratio, int endConcentration, String materialName){
            this.startFluid=startFluid;
            this.endFluid=endFluid;
            this.ratio=ratio;
            this.materialName=materialName;
            this.endConcentration=endConcentration;
    }
    public float getRatio(){return this.ratio;}
    public Fluid getConcentratedFluid(){return this.endFluid;}
    public float getBaseConcentration(){return this.endConcentration;}
    public String getMaterialName(){return this.materialName;}

    public Fluid getFluid(){return this.startFluid;}

}
