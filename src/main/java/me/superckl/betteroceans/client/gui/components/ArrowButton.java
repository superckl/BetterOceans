package me.superckl.betteroceans.client.gui.components;

import me.superckl.betteroceans.common.reference.RenderData;
import me.superckl.betteroceans.common.utility.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ArrowButton extends GuiButton{

	private final boolean left;

	public ArrowButton(final int id, final int x, final int y, final int width, final int height, final String label, final boolean left) {
		super(id ,x, y, width, height, label);
		this.left = left;
	}

	@Override
	public void drawButton(final Minecraft mc, final int p_146112_2_, final int p_146112_3_){
		if(!this.visible)
			return;
		this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
		if(this.left)
			RenderHelper.drawTexturedRect(RenderData.ARROW_BUTTON_LEFT, this.xPosition, this.yPosition, this.getHoverState(this.field_146123_n) == 2 && this.enabled ? 12:0, 0, 12, 12, 24, 12, 1F);
		else
			RenderHelper.drawTexturedRect(RenderData.ARROW_BUTTON_RIGHT, this.xPosition, this.yPosition, this.getHoverState(this.field_146123_n) == 2 && this.enabled ? 12:0, 0, 12, 12, 24, 12, 1F);
	}


}
