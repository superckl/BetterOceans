package me.superckl.betteroceans.client.gui.components;

import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.utility.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class ArrowButton extends GuiButton{

	private final ResourceLocation textureLeft = new ResourceLocation(ModData.MOD_ID+":textures/gui/leftarrow.png");
	private final ResourceLocation textureRight = new ResourceLocation(ModData.MOD_ID+":textures/gui/rightarrow.png");

	private final boolean left;

	public ArrowButton(final int id, final int x, final int y, final int width, final int height, final String label, final boolean left) {
		super(id ,x, y, width, height, label);
		this.left = left;
	}

	@Override
	public void drawButton(final Minecraft mc, final int p_146112_2_, final int p_146112_3_){
		super.drawButton(mc, p_146112_2_, p_146112_3_);
		if(!this.visible)
			return;
		if(this.left)
			RenderHelper.drawTexturedRect(this.textureLeft, this.xPosition+2, this.yPosition+2, 0, 0, 12, 18, 12, 18, 1);
		else
			RenderHelper.drawTexturedRect(this.textureRight, this.xPosition+2, this.yPosition+2, 0, 0, 12, 18, 12, 18, 1);
	}


}
