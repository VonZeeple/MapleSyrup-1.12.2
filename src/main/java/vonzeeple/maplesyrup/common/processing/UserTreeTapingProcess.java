package vonzeeple.maplesyrup.common.processing;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

import static vonzeeple.maplesyrup.utils.StringUtils.splitBlockstateStr;

public class UserTreeTapingProcess implements IUserProcess {

    public String block;
    public String fluid;
    public float amount;

    public ResourceLocation getResourceLocation(){
        return null;
    }
    public UserTreeTapingProcess(){

    }
    public UserTreeTapingProcess(HashMap<String, String> data_in){

    }

    public void getBlockStateEntry(){
        String[] splitted = splitBlockstateStr(this.block);
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
