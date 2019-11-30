package vonzeeple.maplesyrup.common;

import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vonzeeple.maplesyrup.api.IitemHydrometer;
import vonzeeple.maplesyrup.common.tileEntities.TileEntityEvaporator;

@Mod.EventBusSubscriber
public class HydrometerListener {
    @SubscribeEvent
    public static void useHydrometer(PlayerInteractEvent.RightClickBlock event){
        if(!event.getWorld().isRemote){ return;}

        if(event.getItemStack().getItem() instanceof IitemHydrometer){
            TileEntity te = event.getWorld().getTileEntity(event.getPos());
            if( te instanceof TileEntityEvaporator){
                BlockPos pos = event.getPos();
                event.getEntityPlayer().sendMessage(new TextComponentString(((TileEntityEvaporator)te).getConcentration()));
                event.getWorld().playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F, false);

            }
        }
    }


}
