package me.superckl.betteroceans.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class BoatButton extends GuiButton{

	public BoatButton(final int id, final int x, final int y, final int width, final int height, final String label) {
		super(id ,x, y, width, height, label);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawButton(final Minecraft mc, final int p_146112_2_, final int p_146112_3_){
		super.drawButton(mc, p_146112_2_, p_146112_3_);
		if(!this.visible)
			return;

	}


}
