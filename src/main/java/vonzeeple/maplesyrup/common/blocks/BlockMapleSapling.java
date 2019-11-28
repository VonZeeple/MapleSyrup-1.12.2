package vonzeeple.maplesyrup.common.blocks;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.common.Content;

import vonzeeple.maplesyrup.common.ModConfig;
import vonzeeple.maplesyrup.common.worldgen.WorldGenMapleTree;
import vonzeeple.maplesyrup.common.worldgen.WorldGenMapleTreeClassic;
import vonzeeple.maplesyrup.common.worldgen.WorldGenMapleTreeSmall;

import java.util.Random;

public class BlockMapleSapling extends BlockBush implements IGrowable {

    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);
    private int ModConfig;

    public BlockMapleSapling(){
        super();
        setCreativeTab(MapleSyrup.creativeTab);
        setUnlocalizedName("maple_sapling");
        setRegistryName("maplesyrup:maple_sapling");
        setSoundType(SoundType.PLANT);

    }

    //Same boundingbox as a sapling
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SAPLING_AABB;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return (double)worldIn.rand.nextFloat() < 0.45D;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        generateTree(worldIn, pos, state, rand);
    }


    private void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;

        // try to grow the tree
        BlockMapleLeaves.EnumColor[] color_values = BlockMapleLeaves.EnumColor.values();
        //WorldGenerator worldgenerator = new WorldGenMapleTree(true, color_values[rand.nextInt(color_values.length)]);
        IBlockState leave_state = Content.blockMapleLeaves.getDefaultState().withProperty(BlockMapleLeaves.COLOR, color_values[rand.nextInt(color_values.length)]);
        WorldGenerator worldgenerator;

        final int prob_tree_custom = vonzeeple.maplesyrup.common.ModConfig.Maple_trees.maple_tree_custom;
        final int prob_tree_tall = vonzeeple.maplesyrup.common.ModConfig.Maple_trees.maple_tree_tall;
        final int prob_tree_small = vonzeeple.maplesyrup.common.ModConfig.Maple_trees.maple_tree_small;
        int choice = rand.nextInt(prob_tree_custom+prob_tree_small+prob_tree_tall);
        boolean flag = true;
        if(choice > 0) {
            // remove the sapling
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
            if (choice < prob_tree_custom) {
                worldgenerator = new WorldGenMapleTree(true, Content.blockMapleLog.getDefaultState(), leave_state);
            } else if (choice < prob_tree_custom + prob_tree_small) {
                worldgenerator = new WorldGenMapleTreeSmall(true, Content.blockMapleLog.getDefaultState(), leave_state);
            } else {
                worldgenerator = new WorldGenMapleTreeClassic(true, Content.blockMapleLog.getDefaultState(), leave_state);
            }
            //If we cant generate the tree, replant the sapling
            if (!worldgenerator.generate(worldIn, rand, pos)){worldIn.setBlockState(pos, Content.blockMapleSapling.getDefaultState(), 4);}
        }



    }




}
