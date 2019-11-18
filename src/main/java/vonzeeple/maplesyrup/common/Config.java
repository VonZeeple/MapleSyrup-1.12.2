package vonzeeple.maplesyrup.common;


import vonzeeple.maplesyrup.MapleSyrup;

@net.minecraftforge.common.config.Config(modid = MapleSyrup.MODID)
public class Config {
    public static SubCategory subcat = new SubCategory();

    private static class SubCategory {
        public boolean someBool;
        public int relatedInt;
    }
}
