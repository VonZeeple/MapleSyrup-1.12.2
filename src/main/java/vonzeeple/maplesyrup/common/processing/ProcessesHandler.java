package vonzeeple.maplesyrup.common.processing;

import com.google.common.base.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.Map;


public class ProcessesHandler {

    private static ProcessesHandler instance;

    private ProcessesHandler(){
    }

    public static EvaporationProcess find_evaporation_recipe( Fluid fluid){
        IForgeRegistry<EvaporationProcess> registry = GameRegistry.findRegistry(EvaporationProcess.class);
        for(Map.Entry<ResourceLocation,EvaporationProcess> entry : registry.getEntries()){
            if(entry.getValue().matches(fluid)){
                return entry.getValue();
            }
        }
        return null;
    }


    public static ProcessesHandler get_instance(){
        if(instance == null ){
            instance = new ProcessesHandler();
        }
        return instance;
    }

    public void PreInit(){

    }


    public static <T> T cast(@Nullable Object o)
    {
        return o == null ? null : (T) o;
    }

    private IBlockState Find_BlockState(String domain, String block_id, String property_name, String value_name){
        Block block = Block.REGISTRY.getObject(new ResourceLocation(domain+":"+block_id));
        if(block == Blocks.AIR){return null;}
        IProperty<?> property = block.getBlockState().getProperty(property_name);
        if(property == null){return null;}
        Optional<?> value = property.parseValue(value_name);

        if(value.isPresent()){
            return block.getDefaultState().withProperty(property, cast(value.get()));
        }
        return null;
    }

    public void Init(){
        ProcessLoader evapProcessLoader = new ProcessLoader("/processing/evaporator",UserEvaporationProcess.class);
        evapProcessLoader.registerProcesses();
        //ProcessLoader tappingProcessLoader = new ProcessLoader("/processing/tree_tapping",UserTreeTapingProcess.class);
        //tappingProcessLoader.registerProcesses();
    }

    public void PostInit(){

    }



}
