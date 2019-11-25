package vonzeeple.maplesyrup.common.worldgen;

import java.util.Random;


import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import vonzeeple.maplesyrup.common.Content;
import vonzeeple.maplesyrup.common.blocks.BlockMapleLeaves;


public class WorldGenMapleTree extends WorldGenAbstractTree{

    private static final IBlockState LOG = Content.blockMapleLog.getDefaultState();
    private final boolean useExtraRandomHeight;
    private BlockMapleLeaves.EnumColor COLOR;

    public WorldGenMapleTree(boolean notify, boolean useExtraRandomHeightIn, BlockMapleLeaves.EnumColor color)
    {
        super(notify);
        this.useExtraRandomHeight = useExtraRandomHeightIn;
        this.COLOR = color;
    }

    public boolean placeTrunk(World worldIn, BlockPos pos, int height, boolean doPlace){
        if(pos.getY() + height + 1 > worldIn.getHeight()){return false;}
        //Using MutableBlockPos prevent the creation of a big number of instances of BlockPos
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        //Detect obstacle for the trunk
        boolean flag = true;
        for (int j = pos.getY(); j <= pos.getY() + 1 + height; ++j)
        {
            if (j >= 0 && j < worldIn.getHeight()){
                blockpos$mutableblockpos.setPos(pos.getX(), j, pos.getZ());
                if (!this.isReplaceable(worldIn,blockpos$mutableblockpos)) {
                    flag = false;
                }else{
                    if(doPlace){
                        this.setBlockAndNotifyAdequately(worldIn, blockpos$mutableblockpos, LOG);
                    }
                }
            }
        }

        return flag;
    }

    public boolean placeLeaveNode(World worldIn, BlockPos pos, IBlockState LEAF, boolean doPlace){
        boolean flag = true;
        //Place a log at center of node
        IBlockState state = worldIn.getBlockState(pos);
        if (state.getBlock().isAir(state, worldIn, pos)) {
            if(doPlace){
                this.setBlockAndNotifyAdequately(worldIn, pos, LOG);
            }
        }else{flag = false;}

        int radius = 2;
        //Place leaves
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        blockpos$mutableblockpos.setPos(pos);
        for(int x = pos.getX()-radius; x <=pos.getX()+radius; ++x){
            for(int z = pos.getZ()-radius; z <= pos.getZ()+radius; ++z){
                for(int y = pos.getY()-radius; y<= pos.getY()+radius; ++y){
                    if(pos.distanceSq(x,y,z) > radius*radius+1){continue;}
                    blockpos$mutableblockpos.setPos(x,y,z);
                    state = worldIn.getBlockState(blockpos$mutableblockpos);
                    if (state.getBlock().isAir(state, worldIn, blockpos$mutableblockpos)) {
                        if(doPlace){
                            this.setBlockAndNotifyAdequately(worldIn, blockpos$mutableblockpos, LEAF);
                        }
                    }else{flag = false;}
                }
            }
        }

        return flag;
    }

    public boolean isPositionValid(World worldIn, BlockPos pos){
        //Check the world boundaries
        if(pos.getY() < 1 || pos.getY() + 1 > worldIn.getHeight()){return false;}

        //Check if the soil can sustain plants
        BlockPos down = pos.down();
        IBlockState state = worldIn.getBlockState(down);
        boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, down, net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.SAPLING);
        if (!isSoil){return false;}
        return true;
    }
    public boolean generate(World worldIn, Random rand, BlockPos position) {

        IBlockState LEAF = Content.blockMapleLeaves.getDefaultState().withProperty(BlockMapleLeaves.COLOR, this.COLOR);

        //Height of the main trunk
        int height = 8 + rand.nextInt(6);

        if (this.useExtraRandomHeight) {
            height += rand.nextInt(7);
        }

        //Checking if the position of the sapling is valid
        if (!isPositionValid(worldIn, position)) { return false; }
        if (!placeTrunk(worldIn, position, height, false)) { return false; }

        //We notify the growth on the soil block
        BlockPos down = position.down();
        IBlockState state = worldIn.getBlockState(down);
        state.getBlock().onPlantGrow(state, worldIn, down, position);

        placeTrunk(worldIn, position, height, true);

        //We now randomly place leaves nodes along the trunk
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        for (int y = position.getY() + 4 + rand.nextInt(2); y <= position.getY() + height-2; ++y) {
            blockpos$mutableblockpos.setPos(position.getX(),y,position.getZ());
            for(EnumFacing facing:EnumFacing.HORIZONTALS){
                if(rand.nextInt(3)< 1){
                   placeLeaveNode(worldIn,blockpos$mutableblockpos.offset(facing,3), LEAF,true);
                }
            }
        }
        //We place a leave node in top
        placeLeaveNode(worldIn,blockpos$mutableblockpos.offset(EnumFacing.UP,3), LEAF,true);


        return true;
    }
}
