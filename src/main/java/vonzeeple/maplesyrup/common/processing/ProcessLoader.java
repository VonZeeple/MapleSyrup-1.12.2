package vonzeeple.maplesyrup.common.processing;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.io.FilenameUtils;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.utils.JsonParser;
import vonzeeple.maplesyrup.utils.StringUtils;

public class ProcessLoader {

    private String path;
    private Class<? extends IUserProcess> processClass;

    public ProcessLoader( String path ,Class<? extends IUserProcess> processClass){
        this.path = path;
        this.processClass = processClass;
    }

    public boolean registerProcesses(){
        ModContainer mod = Loader.instance().activeModContainer();
        JsonContext ctx = new JsonContext(mod.getModId());

        //root -> { check things in root return true; } Confirms that root is the root directory
        //(root, file) -> {do something to file, return true if valid recipe}
        return CraftingHelper.findFiles(mod, "assets/" + mod.getModId() + this.path,
                root -> true,
                (root, file) ->
                {
                    String relative = root.relativize(file).toString();
                    if (!"json".equals(FilenameUtils.getExtension(file.toString())))//If file isn't a JSON file, we skip
                        return true;

                    JsonElement json = JsonParser.load_JSON(file.toFile());

                    if(json == null){
                        return true;
                    }

                    if(json.isJsonArray()){
                        return true;
                    }
                    if(json.isJsonObject()){
                        String filename = StringUtils.removeFileExtension(file.getFileName().toString());
                        register_process_from_json(json,new ResourceLocation(mod.getModId()+':'+filename));
                    }

                    return true;
                },
                true, true
        );
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

}
