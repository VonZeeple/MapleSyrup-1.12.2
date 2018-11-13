package vonzeeple.maplesyrup.client.render;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import org.lwjgl.opengl.GL11;
import vonzeeple.maplesyrup.common.Content;
import vonzeeple.maplesyrup.common.tileEntities.TileEntityTreeTap;

public class TESRtreetap extends TileEntitySpecialRenderer<TileEntityTreeTap> {
    @Override
    public void render(TileEntityTreeTap te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

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


        if(te.isFull()){
            BlockPos blockPos = te.getPos();
            IBlockState state = getWorld().getBlockState(blockPos);

            GlStateManager.translate(x, y, z);
            GlStateManager.translate(0.5, 0, 0.5);

            switch (state.getValue(BlockHorizontal.FACING)) {
                case NORTH:
                    GlStateManager.rotate(0f, 0f, 1f, 0f);
                    break;
                    case SOUTH:
                        GlStateManager.rotate(180f, 0f, 1f, 0f);
                        break;
                        case WEST:
                            GlStateManager.rotate(90f, 0f, 1f, 0f);
                            break;
                            case EAST:
                                default:
                                    GlStateManager.rotate(270f, 0f, 1f, 0f);
            }

            GlStateManager.translate(0, -0.01, 0.2);

            Fluid fluid=te.getFluid().getFluid();
            if(fluid!=null){
                    Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                    TextureAtlasSprite sprite =  Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getStill().toString());

                    float d=0.3f;
                    float h=0.5f;
                    if(sprite != null)
                    {

                        wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                        wr.pos(-d/2, h, -d/2).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
                        wr.pos(-d/2, h, d/2).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
                        wr.pos(d/2, h, d/2).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
                        wr.pos(d/2, h, -d/2).tex(sprite.getMinU(), sprite.getMinV()).endVertex();

                        tes.draw();

                    }
                }


            }
        GlStateManager.popMatrix();
    }
}
