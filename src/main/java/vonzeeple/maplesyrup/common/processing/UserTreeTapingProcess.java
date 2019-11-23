package vonzeeple.maplesyrup.common.processing;

import net.minecraft.util.ResourceLocation;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.utils.StringUtils;

import java.util.HashMap;

import static vonzeeple.maplesyrup.utils.StringUtils.splitBlockstateStr;
import static vonzeeple.maplesyrup.utils.StringUtils.splitPropertyStr;

public class UserTreeTapingProcess implements IUserProcess {

    public String block;
    public String fluid;
    public int amount;

    public ResourceLocation getResourceLocation(){
        return null;
    }
    public UserTreeTapingProcess(){

    }

    public BlockStateEntry getBlockStateEntry(){
        String[] splitted = splitBlockstateStr(this.block);
        HashMap<String, String> properties = new HashMap<>();
        for(int i=1; i<splitted.length; i++){
            String[] prop_str = splitPropertyStr(splitted[i]);
            properties.put(prop_str[0], prop_str[1]);
        }
        return new BlockStateEntry(splitted[0], properties);
    }

    @Override
    public TappingProcess getProcess(){
        return new TappingProcess(this.getBlockStateEntry(), fluid, amount);
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
