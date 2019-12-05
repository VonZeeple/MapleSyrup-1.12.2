package vonzeeple.maplesyrup.common.processing;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.io.FilenameUtils;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.utils.JsonParser;
import vonzeeple.maplesyrup.utils.StringUtils;
import java.io.File;


public class ProcessLoader {

    private File configFolder;
    private Class<? extends IUserProcess> processClass;

    public ProcessLoader(File folder , Class<? extends IUserProcess> processClass){
        this.configFolder = folder;
        this.processClass = processClass;
    }

    public boolean registerProcesses(){
        if(configFolder == null){return false;}

        if(!configFolder.exists()){

            return false;
        }

        for(File file: configFolder.listFiles()){
            if (!"json".equals(FilenameUtils.getExtension(file.toString())))//If file isn't a JSON file, we skip
                return false;

            JsonElement json = JsonParser.load_JSON(file.toPath());
            if(json == null){
                return false;
            }

            if(json.isJsonArray()){
                return false;
            }
            if(json.isJsonObject()){
                String filename = StringUtils.removeFileExtension(file.toPath().getFileName().toString());
                register_process_from_json(json,new ResourceLocation(MapleSyrup.MODID+':'+filename));
            }
        }
        return true;
    }

    private void register_process_from_json(JsonElement json, ResourceLocation location){

        IUserProcess process = new Gson().fromJson(json, processClass);
        MapleSyrup.logger.info(location);
        IProcess proc = process.getProcess();
        if(proc == null){return;}
        if(!proc.isValid()){return;}
        proc.setRegistryName(location);
        GameRegistry.findRegistry(proc.getClass()).register(proc);

    }

    public boolean add_default_recipes(String path){
        ModContainer mod = Loader.instance().activeModContainer();

        if(!configFolder.exists()){
            if(!configFolder.mkdirs()) {
                MapleSyrup.logger.error("Cannot create config folder");
                return true;
            }
        }

        //root -> { check things in root return true; } Confirms that root is the root directory
        //(root, file) -> {do something to file, return true if valid recipe}
        return CraftingHelper.findFiles(mod, "assets/" + mod.getModId() + path,
                root -> true,
                (root, file) ->
                {
                    if (!"json".equals(FilenameUtils.getExtension(file.toString())))//If file isn't a JSON file, we skip
                        return true;

                    JsonElement json = JsonParser.load_JSON(file);

                    JsonParser.write_JSON(json,configFolder.toPath(), file.getFileName().toString());

                    return true;
                },
                true, true);
    }

}
