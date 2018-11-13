package vonzeeple.maplesyrup.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import vonzeeple.maplesyrup.MapleSyrup;
import vonzeeple.maplesyrup.common.tileEntities.ContainerEvaporator;
import vonzeeple.maplesyrup.common.tileEntities.TileEntityEvaporator;

public class GuiEvaporator extends GuiContainer {

    public static final int WIDTH = 175;
    public static final int HEIGHT = 170;
    public TileEntityEvaporator te;

    private static final ResourceLocation background = new ResourceLocation(MapleSyrup.MODID, "textures/gui/evaporator.png");

    public GuiEvaporator(TileEntityEvaporator tileEntity, ContainerEvaporator container) {
        super(container);
        this.te=tileEntity;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        double ratio=(te.burningTime==0)? 0:te.burningTimeLeft*1./te.burningTime;
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        drawTexturedModalRect(guiLeft+81, guiTop+43+(int)(14*(1-ratio)), 176, (int)(14*(1-ratio)), 14, (int)(14*(ratio)));
        this.fontRenderer.drawString("Evaporator",guiLeft+5,guiTop+5,0x665f56);
    }

}
