package vonzeeple.maplesyrup.common.worldgen;

import java.util.Random;


import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import vonzeeple.maplesyrup.common.Content;
import vonzeeple.maplesyrup.common.blocks.BlockMapleLeaves;


public class WorldGenMapleTree extends WorldGenAbstractTree{

    private static final IBlockState LOG = Content.blockMapleLog.getDefaultState();
    //private static final IBlockState LEAF = Content.mapleLeaves.getDefaultState().withProperty(BlockOldLeaf.CHECK_DECAY, Boolean.valueOf(false));
    private static IBlockState LEAF;
    private final boolean useExtraRandomHeight;

    public WorldGenMapleTree(boolean notify, boolean useExtraRandomHeightIn)
    {
        super(notify);
        this.useExtraRandomHeight = useExtraRandomHeightIn;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {

        LEAF=Content.blockMapleLeaves.getDefaultState().withProperty(BlockMapleLeaves.colorIndex,rand.nextInt(4));
        //Height of the main trunk
        int i = 8 + rand.nextInt(6);

        if (this.useExtraRandomHeight)
        {
            i += rand.nextInt(7);
        }

        boolean flag = true;

        //Checking if the Y position of the sapling is valid
        if (position.getY() >= 1 && position.getY() + i + 1 <= 256)
        {
            for (int j = position.getY(); j <= position.getY() + 1 + i; ++j)
            {
                int k = 1;//size of tree's bounding box

                if (j == position.getY())
                {
                    k = 0;
                }

                if (j >= position.getY() + 1 + i - 2)
                {
                    k = 3;
                }

                //Using MutableBlockPos prevent the creation of a big number of instances of BlockPos

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                //Detect obstacle in the tree's bounding box
                for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l)
                {
                    for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1)
                    {
                        if (j >= 0 && j < worldIn.getHeight())
                        {
                            if (!this.isReplaceable(worldIn, blockpos$mutableblockpos.setPos(l, j, i1)))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                BlockPos down = position.down();
                IBlockState state = worldIn.getBlockState(down);
                boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, down, net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.SAPLING);

                if (isSoil && position.getY() < worldIn.getHeight() - i - 1)
                {
                    state.getBlock().onPlantGrow(state, worldIn, down, position);

                    for (int i2 = position.getY() + 3 + rand.nextInt(2); i2 <= position.getY() + i; ++i2)
                    {
                        //int k2 = i2 - (position.getY() + i);
                        //int l2 = 1 - k2 / 2;

                        int k2 = i2 - (position.getY() + i);
                        int l2 = 2;//Radius of the leaves

                        for (int i3 = position.getX() - l2; i3 <= position.getX() + l2; ++i3)
                        {
                            int j1 = i3 - position.getX();

                            for (int k1 = position.getZ() - l2; k1 <= position.getZ() + l2; ++k1)
                            {
                                int l1 = k1 - position.getZ();

                                if (Math.abs(j1) != l2 || Math.abs(l1) != l2 || rand.nextInt(2) != 0 && k2 != 0)
                                {
                                    BlockPos blockpos = new BlockPos(i3, i2, k1);
                                    IBlockState state2 = worldIn.getBlockState(blockpos);

                                    if (state2.getBlock().isAir(state2, worldIn, blockpos) || state2.getBlock().isAir(state2, worldIn, blockpos))
                                    {
                                        this.setBlockAndNotifyAdequately(worldIn, blockpos, LEAF);
                                    }
                                }
                            }
                        }
                    }

                    for (int j2 = 0; j2 < i; ++j2)
                    {
                        BlockPos upN = position.up(j2);
                        IBlockState state2 = worldIn.getBlockState(upN);

                        if (state2.getBlock().isAir(state2, worldIn, upN) || state2.getBlock().isLeaves(state2, worldIn, upN))
                        {
                            this.setBlockAndNotifyAdequately(worldIn, position.up(j2), LOG);
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
