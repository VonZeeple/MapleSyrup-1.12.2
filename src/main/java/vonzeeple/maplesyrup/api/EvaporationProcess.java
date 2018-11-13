package vonzeeple.maplesyrup.api;

public class EvaporationProcess {
    String concentratedFluid;
    float ratio;
    int endConcentration;
    String materialName;

    public EvaporationProcess(String fluid, float ratio, int endConcentration, String materialName){
            this.concentratedFluid=fluid;
            this.ratio=ratio;
            this.materialName=materialName;
            this.endConcentration=endConcentration;
    }
    public float getRatio(){return this.ratio;}
    public String getConcentratedFluid(){return this.concentratedFluid;}
    public float getEndConcentration(){return this.endConcentration;}
    public String getMaterialName(){return this.materialName;}

}
