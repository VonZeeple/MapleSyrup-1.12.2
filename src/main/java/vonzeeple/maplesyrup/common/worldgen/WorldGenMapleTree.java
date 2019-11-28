package vonzeeple.maplesyrup.common.worldgen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import vonzeeple.maplesyrup.common.blocks.BlockMapleLeaves;
import vonzeeple.maplesyrup.common.blocks.BlockMapleLog;


public class WorldGenMapleTree extends WorldGenAbstractTree{

    private IBlockState LOG;
    private IBlockState LEAVES;

    public WorldGenMapleTree(boolean notify,  IBlockState wood_state, IBlockState leaves_state)
    {
        super(notify);
        this.LOG = wood_state;
        this.LEAVES = leaves_state.withProperty(BlockMapleLeaves.CHECK_DECAY, true);
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
                if(!placeLog(worldIn, EnumFacing.UP, blockpos$mutableblockpos, doPlace)){flag = false;}

            }
        }

        return flag;
    }

    private boolean placeLog(World worldIn, EnumFacing facing, BlockPos pos, boolean doPlace){
        IBlockState state = worldIn.getBlockState(pos);
        boolean flag = true;
        if (state.getBlock().isReplaceable(worldIn,pos) || state.getBlock().isLeaves(state,worldIn,pos) || state.getBlock() instanceof BlockMapleLog) {
            if(doPlace){
                if(!(state.getBlock() instanceof BlockMapleLog)){
                    this.setBlockAndNotifyAdequately(worldIn, pos, LOG.withProperty(BlockMapleLog.LOG_AXIS, BlockMapleLog.EnumAxis.fromFacingAxis(facing.getAxis())));
                }
            }
        }else{flag =  false;}
        return flag;
    }

    private boolean placeLeaves(World worldIn, BlockPos pos, IBlockState leaves_state, boolean doPlace){
        IBlockState state = worldIn.getBlockState(pos);
        boolean flag = true;
        if (state.getBlock().isAir(state, worldIn,pos)) {
            if(doPlace){
                this.setBlockAndNotifyAdequately(worldIn, pos, leaves_state);
            }
        }else{flag = false;}
        return flag;
    }

    public boolean placeLeaveNode(World worldIn, EnumFacing facing,BlockPos origin, BlockPos pos, IBlockState LEAF, Random rand, boolean doPlace){
        boolean flag = true;
        //Place a log at center of node
        if(!placeLog(worldIn, facing, pos, doPlace)){flag = false;}
        //place logs between the trunk and the node

        int radius = 2;
        //Place leaves around the node
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        blockpos$mutableblockpos.setPos(pos);
        for(int x = pos.getX()-radius; x <=pos.getX()+radius; ++x){
            for(int z = pos.getZ()-radius; z <= pos.getZ()+radius; ++z){
                for(int y = pos.getY()-radius; y<= pos.getY()+radius; ++y){
                    double dist = pos.distanceSq(x,y,z);
                    if(dist > radius*radius+1+rand.nextInt(2)*2){continue;}
                    blockpos$mutableblockpos.setPos(x,y,z);
                    IBlockState state = worldIn.getBlockState(blockpos$mutableblockpos);
                    if(!placeLeaves(worldIn, blockpos$mutableblockpos, LEAF,doPlace)){flag = false;}

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

        //Height of the main trunk
        int height = 7 + rand.nextInt(4);


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
        for (int y = position.getY() + 5 + rand.nextInt(2); y <= position.getY() + height-1; ++y) {
            blockpos$mutableblockpos.setPos(position.getX(),y,position.getZ());
            int node_radius = y<position.getY()+8 ? 2 : 1;
            for(EnumFacing facing:EnumFacing.HORIZONTALS){
                if(rand.nextInt(4)<3) {
                    placeLeaveNode(worldIn, facing,blockpos$mutableblockpos ,blockpos$mutableblockpos.offset(facing, rand.nextInt(node_radius+1)), LEAVES, rand, true);
                }
            }
        }
        //We place a leave node in top
        placeLeaveNode(worldIn,EnumFacing.UP,blockpos$mutableblockpos,blockpos$mutableblockpos.offset(EnumFacing.UP,2), LEAVES, rand,true);


        return true;
    }
}
