package vonzeeple.maplesyrup.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import vonzeeple.maplesyrup.common.tileEntities.ContainerEvaporator;
import vonzeeple.maplesyrup.client.gui.GuiEvaporator;
import vonzeeple.maplesyrup.common.tileEntities.TileEntityEvaporator;

public class GuiProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityEvaporator) {
            return new ContainerEvaporator(player.inventory, (TileEntityEvaporator) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityEvaporator) {
            TileEntityEvaporator containerTileEntity = (TileEntityEvaporator) te;
            return new GuiEvaporator(containerTileEntity, new ContainerEvaporator(player.inventory, containerTileEntity));
        }
        return null;
    }
}
