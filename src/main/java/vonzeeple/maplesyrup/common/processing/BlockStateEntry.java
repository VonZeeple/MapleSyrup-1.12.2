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
    private Map<IProperty, Optional> properties = new HashMap<>();

    public BlockStateEntry(String block_in){
        if(!GameRegistry.findRegistry(Block.class).containsKey(new ResourceLocation(block_in))){
            return;
        }
        block = GameRegistry.findRegistry(Block.class).getValue(new ResourceLocation(block_in));
    }

    public BlockStateEntry(String block_in, HashMap<String,String> properties_in ){
        this(block_in);
        if(block == null){return;}
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
        if(!(object instanceof IBlockState)){
            return false;
        }
        Block block = ((IBlockState) object).getBlock();
        if( Block.REGISTRY.getIDForObject(block) != Block.REGISTRY.getIDForObject(this.block)){
            return false;
        }
        //Test of properties
        Map<IProperty<?>, Comparable<?>> block_properties = ((IBlockState) object).getProperties();

        for( Map.Entry<IProperty, Optional> entry: properties.entrySet()){
            if(block_properties.containsKey(entry.getKey())){
                if ( entry.getValue().get() != block_properties.get(entry.getKey())){
                        return false;

                }
            }
        }

        return true;
    }

}
