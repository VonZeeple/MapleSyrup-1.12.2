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
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.common.Content;
import vonzeeple.maplesyrup.common.worldgen.WorldGenMapleTree;
import java.util.Random;

public class BlockMapleSapling extends BlockBush implements IGrowable {

    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

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
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        generateTree(worldIn, pos, state, rand);
    }


    public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
        WorldGenerator worldgenerator = (WorldGenerator)(rand.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true));
        //boolean flag = false;
        // remove the sapling
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
        // try to grow the tree
        worldgenerator = new WorldGenMapleTree(true, false);

        if (!worldgenerator.generate(worldIn, rand, pos)){worldIn.setBlockState(pos, Content.blockMapleSapling.getDefaultState(), 4);}
    }




}
