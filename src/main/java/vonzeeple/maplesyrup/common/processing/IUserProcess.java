package vonzeeple.maplesyrup.common.processing;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;

public interface IUserProcess {

     ResourceLocation getResourceLocation();
     boolean isValid();

    <T extends IProcess> T getProcess();



}
