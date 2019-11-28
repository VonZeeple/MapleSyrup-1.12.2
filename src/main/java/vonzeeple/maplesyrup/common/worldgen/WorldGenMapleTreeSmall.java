package vonzeeple.maplesyrup.common.worldgen;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTrees;

import java.util.Random;

public class WorldGenMapleTreeSmall extends WorldGenTrees {

    public WorldGenMapleTreeSmall(boolean notify, IBlockState wood_state, IBlockState leaves_state) {
        super(notify, 6, wood_state, leaves_state, false);
    }
}
