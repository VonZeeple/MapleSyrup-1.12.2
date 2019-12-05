package vonzeeple.maplesyrup.utils;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import net.minecraftforge.common.config.Configuration;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class JsonParser {

    static public void write_JSON(JsonElement element, Path path, String filename){
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        try{
            JsonWriter writer = new JsonWriter(new FileWriter(path.resolve(filename).toFile()));
            gson.toJson(element, writer);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    static public JsonElement load_JSON(Path file){
        BufferedReader buffer = null;
        Configuration.UnicodeInputStreamReader input = null;

        try {
                buffer = Files.newBufferedReader(file);
                try {
                    return new Gson().fromJson(buffer, JsonElement.class);
                }catch(JsonSyntaxException e){
                return null;}
        } catch (IOException e) {
        return null;
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }



    }
}
