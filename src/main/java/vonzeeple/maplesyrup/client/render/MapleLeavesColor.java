package vonzeeple.maplesyrup.client.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import org.apache.commons.io.IOUtils;
import vonzeeple.maplesyrup.MapleSyrup;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;

public class MapleLeavesColor implements IBlockColor {

    private static final int color = 0xff0000;

    private static final ResourceLocation LOC_MAPLELEAVES_PNG = new ResourceLocation(MapleSyrup.MODID, "textures/blocks/leaves_maple_color.png");
    private static int[] colorBuffer = new int[65536];
    private static int colorBuffer_width;
    private static int colorBuffer_height;

    public MapleLeavesColor(IResourceManager resourceManadger){

        try {
            colorBuffer=readImageData(resourceManadger, LOC_MAPLELEAVES_PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] readImageData(IResourceManager resourceManager, ResourceLocation imageLocation) throws IOException
    {
        IResource iresource = null;
        int[] aint1;

        try
        {
            iresource = resourceManager.getResource(imageLocation);
            BufferedImage bufferedimage = TextureUtil.readBufferedImage(iresource.getInputStream());
            colorBuffer_width = bufferedimage.getWidth();
            colorBuffer_height = bufferedimage.getHeight();
            int[] aint = new int[colorBuffer_width * colorBuffer_height];
            bufferedimage.getRGB(0, 0, colorBuffer_width , colorBuffer_height, aint, 0, colorBuffer_width);
            aint1 = aint;
        }
        finally
        {
            IOUtils.closeQuietly((Closeable)iresource);
        }

        return aint1;
    }
    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        if(worldIn != null && pos != null) {

            //humidity = humidity * temperature;
            //int i = (int)((1.0D - temperature) * 255.0D);
            //int j = (int)((1.0D - humidity) * 255.0D);
            //int k = j << 8 | i;
            //return k > grassBuffer.length ? -65281 : grassBuffer[k];
            int k=pos.getX()% (colorBuffer_width) + colorBuffer_width*(pos.getZ()%colorBuffer_height);
            return k > colorBuffer.length ? -65281 : colorBuffer[k];

        }
        return colorBuffer[0];
        //return ColorizerFoliage.getFoliageColorBasic();
    }
}
