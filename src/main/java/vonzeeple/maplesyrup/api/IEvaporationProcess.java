package vonzeeple.maplesyrup.api;

import net.minecraftforge.fluids.Fluid;

public interface IEvaporationProcess {

    float getRatio();
    Fluid getConcentratedFluid();
    float getBaseConcentration();
    String getMaterialName();
    Fluid getFluid();
}
