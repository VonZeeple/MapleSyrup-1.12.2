package vonzeeple.maplesyrup.utils;

import com.google.gson.*;
import net.minecraftforge.common.config.Configuration;
import vonzeeple.maplesyrup.MapleSyrup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static net.minecraftforge.common.config.Configuration.DEFAULT_ENCODING;

public class JsonParser {

    static public JsonElement load_JSON(File file){
        BufferedReader buffer = null;
        Configuration.UnicodeInputStreamReader input = null;

        try {
            if (file.canRead()) {
                input = new Configuration.UnicodeInputStreamReader(new FileInputStream(file), DEFAULT_ENCODING);
                buffer = new BufferedReader(input);
                try {
                    return new Gson().fromJson(buffer, JsonElement.class);
                }catch(JsonSyntaxException e){
                return null;}
            } else { return null;
            }
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
