package vonzeeple.maplesyrup.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;
import vonzeeple.maplesyrup.common.tileEntities.TileEntityEvaporator;

public class TESRevaporator extends TileEntitySpecialRenderer<TileEntityEvaporator> {

    @Override
    public void render(TileEntityEvaporator te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        Tessellator tes=Tessellator.getInstance();
        BufferBuilder wr = tes.getBuffer();
        GlStateManager.pushMatrix();

        //New rendering, level in cauldron

        //float h=1f;
        //float w=2f/16f;
        float yoffset=5f/16f;
        float yend=1f/16f;
        float border=2f/16f;
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.color(1f,1f,1f,1);
        GlStateManager.enableLighting();



        FluidStack fluid=te.getFluidStack();
        if(fluid!=null){
            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            TextureAtlasSprite sprite =  Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getFluid().getStill().toString());

            if(sprite != null)
            {

                wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);


                float h2=(1-yend-yoffset)*te.getLevelRatio()+yoffset;
                GlStateManager.translate(x, y, z);
                wr.pos(0f+border, h2, 0f+border).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
                wr.pos(0f+border, h2, 1f-border).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
                wr.pos(1f-border, h2, 1f-border).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
                wr.pos(1f-border, h2, 0f+border).tex(sprite.getMinU(), sprite.getMinV()).endVertex();

                tes.draw();

            }
        }

        GlStateManager.popMatrix();
    }


}
