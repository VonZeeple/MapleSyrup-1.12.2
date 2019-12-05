package vonzeeple.maplesyrup.common;


import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vonzeeple.maplesyrup.MapleSyrup;
import net.minecraftforge.common.config.Config;



@Config(modid = MapleSyrup.MODID)
public class ModConfig {

    public static final Processes processes = new Processes();
    public static class Processes {
        @Config.Comment("Base speed for evaporator")
        @Config.RangeInt(min = 0)
        public int evaporator_base_speed =5;
        @Config.Comment("Add defaults recipies if JSON files are missing")
        public boolean reset_default_recipies = true;
    }

    public static final MapleTrees Maple_trees = new MapleTrees();

    public static class MapleTrees {
        @Config.Comment("Probability to generate custom trees")
        @Config.RangeInt(min = 0, max = 100)
        public int maple_tree_custom = 0;
        @Config.Comment("Probability to generate vanilla style tall trees")
        @Config.RangeInt(min = 0, max = 100)
        public int maple_tree_tall = 100;
        @Config.Comment("Probability to generate vanilla style small trees")
        @Config.RangeInt(min = 0, max = 100)
        public int maple_tree_small = 0;
    }


    @Mod.EventBusSubscriber(modid = MapleSyrup.MODID)
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(MapleSyrup.MODID)) {
                ConfigManager.sync(MapleSyrup.MODID, Config.Type.INSTANCE);
            }
        }
    }
}
