package vonzeeple.maplesyrup.common.processing;

import com.google.common.base.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.common.ModConfig;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Map;


public class ProcessesHandler {

    private static ProcessesHandler instance;

    private static ProcessLoader evapProcessLoader;
    private static ProcessLoader tappingProcessLoader;

    private ProcessesHandler(){
    }

    public static TappingProcess find_treetapping_recipe( Object object){
        IForgeRegistry<TappingProcess> registry = GameRegistry.findRegistry(TappingProcess.class);
        for(Map.Entry<ResourceLocation,TappingProcess> entry : registry.getEntries()){
            if(entry.getValue().matches(object)){
                return entry.getValue();
            }
        }
        return null;
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

    public void PreInit(FMLPreInitializationEvent event){
        evapProcessLoader= new ProcessLoader(new File(event.getModConfigurationDirectory(), "maplesyrup/processing/evaporator"),UserEvaporationProcess.class);
        tappingProcessLoader= new ProcessLoader( new File(event.getModConfigurationDirectory(), "maplesyrup/processing/tree_tapping"),UserTreeTapingProcess.class);
        if(ModConfig.processes.reset_default_recipies) {
            evapProcessLoader.add_default_recipes("/processing/evaporator/");
            tappingProcessLoader.add_default_recipes("/processing/tree_tapping/");
        }
    }


    public void Init(){
        evapProcessLoader.registerProcesses();
        tappingProcessLoader.registerProcesses();
    }

    public void PostInit(){

    }



}
