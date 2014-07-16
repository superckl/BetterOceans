package me.superckl.betteroceans.client.gui;

import me.superckl.betteroceans.common.container.ContainerBasicBoatWorkbench;
import me.superckl.betteroceans.common.entity.EntityWoodenBoat;
import me.superckl.betteroceans.common.entity.tile.TileEntityBasicBoatWorkbench;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.utility.RenderUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiContainerBasicBoatWorkbench extends GuiContainer{

	private final ResourceLocation texture = new ResourceLocation(ModData.MOD_ID+":textures/gui/basicbench.png");
	public GuiContainerBasicBoatWorkbench(final InventoryPlayer inventoryPlayer,
			final TileEntityBasicBoatWorkbench te) {
		super(new ContainerBasicBoatWorkbench(inventoryPlayer, te));
		this.xSize = 322;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int param1, final int param2){
	}

	@Override
	public void updateScreen(){
		super.updateScreen();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float var1, final int var2,
			final int var3) {
		this.mc.renderEngine.bindTexture(this.texture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		final int xStart = (this.width - this.xSize) / 2;
		final int yStart = (this.height - this.ySize) / 2;
		//this.drawTexturedModalRect(xStart, yStart, 0, 0, this.xSize, this.ySize);
		RenderUtil.drawTexturedRect(this.texture, xStart, yStart, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize, 1F);

		final EntityWoodenBoat entity = ((ContainerBasicBoatWorkbench)this.inventorySlots).getEntity();
		// Draw the Entity Model
		final int par1 = this.guiLeft + 195;
		final int par2 = this.guiTop + 52;
		final int scale = 18;
		final float par4 = this.guiLeft + 51;
		final float par5 = this.guiTop + 75 - 50;

		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(par1, par2, 50.0F);
		GL11.glScalef(-scale, scale, scale);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		//float var6 = entity.renderYawOffset;
		final float var7 = entity.rotationYaw;
		final float var8 = entity.rotationPitch;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float) Math.atan(par5 / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
		//entity.renderYawOffset = (float) Math.atan((double) (par4 / 40.0F)) * 20.0F;
		entity.rotationYaw = (float) Math.atan(par4 / 40.0F) * 40.0F;
		entity.rotationPitch = -((float) Math.atan(par5 / 40.0F)) * 20.0F;
		//entity.rotationYawHead = entity.rotationYaw;
		GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		//entity.renderYawOffset = var6;
		entity.rotationYaw = var7+1;
		entity.rotationPitch = var8+1;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
		//TODO

	}

}
