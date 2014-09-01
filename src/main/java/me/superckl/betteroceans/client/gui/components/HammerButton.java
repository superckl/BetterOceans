package me.superckl.betteroceans.client.gui.components;

import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.utility.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class HammerButton extends GuiButton{

	private final ResourceLocation texture = new ResourceLocation(ModData.MOD_ID+":textures/gui/buttoncraft.png");

	public HammerButton(final int p_i1021_1_, final int p_i1021_2_, final int p_i1021_3_,
			final int p_i1021_4_, final int p_i1021_5_, final String p_i1021_6_) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
	}

	@Override
	public void drawButton(final Minecraft mc, final int p_146112_2_, final int p_146112_3_){
		if(!this.visible)
			return;
		this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
		RenderHelper.drawTexturedRect(this.texture, this.xPosition, this.yPosition, this.getHoverState(this.field_146123_n) == 2  && this.enabled ? 18:0, 0, 18, 18, 36, 18, 1F);
	}

}
