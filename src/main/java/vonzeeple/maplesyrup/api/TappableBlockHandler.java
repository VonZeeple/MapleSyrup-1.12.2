package vonzeeple.maplesyrup.api;


import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;

public class TappableBlockHandler {

    static final HashMap<IBlockState, FluidStack> tappableBlocks = new HashMap<IBlockState, FluidStack>();

    public static void registerTappableBlock(IBlockState state, FluidStack fluidStack)
    {
        if(state != null && fluidStack != null){
            tappableBlocks.put(state, fluidStack);
        }
    }

    public static Boolean isTappable(IBlockState state){
        if(state != null){
            return tappableBlocks.containsKey(state);}
        return false;
    }

    public static FluidStack getTappableSap(IBlockState state)
    {

        if(state != null)
        {
            if(tappableBlocks.containsKey(state))
                return tappableBlocks.get(state);
        }
        return null;
    }

}
