package vonzeeple.maplesyrup.common.processing;

import com.google.common.base.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;


import java.util.HashMap;
import java.util.Map;

public class BlockStateEntry {

    private Block block;
    private Map<IProperty, Object> properties;



    public BlockStateEntry(String block_in, HashMap<String,String> properties_in ){
        if(!GameRegistry.findRegistry(Block.class).containsKey(new ResourceLocation(block_in))){
            return;
        }
        Block block = GameRegistry.findRegistry(Block.class).getValue(new ResourceLocation(block_in));
        BlockStateContainer blockStateCont = block.getBlockState();

        for(String s : properties_in.keySet()){
            IProperty prop = blockStateCont.getProperty(s);
            if(prop != null){
                Optional value = prop.parseValue(properties_in.get(s));
                if( value.isPresent()){
                    properties.put(prop, value);
                }
            }
        }
    }

    public boolean test(Object object){
        if(!(object instanceof Block || object instanceof IBlockState)){
            return false;
        }
        if (! (Block.REGISTRY.getIDForObject((Block)object) == Block.REGISTRY.getIDForObject(block))){
            return false;
        }
        //Test of properties

        return false;
    }

}
