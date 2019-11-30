package vonzeeple.maplesyrup.common;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vonzeeple.maplesyrup.MapleSyrup;

@Mod.EventBusSubscriber
public class LootTableHandler {

    public static void registerLoot(){
        LootTableList.register(new ResourceLocation(MapleSyrup.MODID, "maple_saplings"));
    }

    @SubscribeEvent
    public static void lootLoad(LootTableLoadEvent event) {
        if (event.getName().toString().equals("minecraft:chests/village_blacksmith")) {

            LootEntry entry = new LootEntryTable(new ResourceLocation("maplesyrup:maple_saplings"), 50,0,new LootCondition[0],"maplesyrup_sapling_entry");

            LootPool pool = new LootPool(new LootEntry[] {entry},new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "maplesyrup_sapling_pool");

            event.getTable().addPool(pool);
        }
    }
}
