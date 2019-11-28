package vonzeeple.maplesyrup.common.blocks;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.common.Content;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class BlockMapleLeaves extends BlockLeaves implements ICustomMappedBlock {

    public static final PropertyEnum COLOR = PropertyEnum.create("color", EnumColor.class);

    public BlockMapleLeaves(){
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
        setCreativeTab(MapleSyrup.creativeTab);
        setUnlocalizedName("maple_leaves");
        setRegistryName("maplesyrup:maple_leaves");
    }

    public static boolean isReplaceable(World worldIn , BlockPos pos){
        return true;
    }
    public static enum EnumColor implements IStringSerializable {
        RED(0, "red"),
        YELLOW(1, "yellow"),
        ORANGE(2, "orange"),
        GREEN(3, "green");

        @Override
        public String toString() {
            return this.name;
        }

        public static EnumColor byMetadata(int meta) {
            return META_LOOKUP[meta % META_LOOKUP.length];
        }

        public String getName() {
            return this.name;
        }

        public int getMeta() {
            return this.meta;
        }

        private final int meta;
        private final String name;
        private static final EnumColor[] META_LOOKUP = new EnumColor[values().length];

        private EnumColor(int i_meta, String i_name) {
            this.meta = i_meta;
            this.name = i_name;
        }

        static {
            for (EnumColor colour : values()) {
                META_LOOKUP[colour.getMeta()] = colour;
            }
        }
    }


    public int getColor(int meta){return meta%4;}
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(COLOR, EnumColor.byMetadata(meta)).withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((EnumColor)state.getValue(COLOR)).getMeta();

        if (!(Boolean) state.getValue(DECAYABLE))
        {
            i |= 4;
        }

        if ((Boolean) state.getValue(CHECK_DECAY))
        {
            i |= 8;
        }

        return i;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return Blocks.LEAVES.getBlockLayer();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return Blocks.LEAVES.shouldSideBeRendered(state, world, pos, side);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Content.blockMapleSapling);
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this, 1,0));
        items.add(new ItemStack(this, 1,1));
        items.add(new ItemStack(this, 1,2));
        items.add(new ItemStack(this, 1,3));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {COLOR,CHECK_DECAY, DECAYABLE});
    }
    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return null;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
         return NonNullList.withSize(1, new ItemStack(this, 1));
    }

    // use a custom state mapper which will ignore the DECAY property of leaves
    public StateMapperBase getCustomStateMapper(){
        return new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return new ModelResourceLocation(MapleSyrup.MODID.toLowerCase() + ":maple_leaves", "color="+((EnumColor)state.getValue(COLOR)).getName());
            }
        };
    }
}
