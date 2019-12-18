package vonzeeple.maplesyrup.common.blocks;

import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICustomMappedBlock {

    @SideOnly(Side.CLIENT)
    StateMapperBase getCustomStateMapper();
}
