package vonzeeple.maplesyrup.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import vonzeeple.maplesyrup.MapleSyrup;

public class BlockFluid extends BlockFluidClassic {

    public String name;

    public BlockFluid(Fluid fluid,String name) {
        super(fluid, Material.WATER);
        setCreativeTab(MapleSyrup.creativeTab);
        //setUnlocalizedName("maple_sap_fluid");
        this.name=name;
        setUnlocalizedName(name);
        setRegistryName(MapleSyrup.MODID+":"+name);
    }



    public StateMapperBase getCustomStateMapper(){
        String name= this.name;
        return new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return new ModelResourceLocation(MapleSyrup.MODID.toLowerCase() + ":fluids", name);
            }
        };
    }
}
