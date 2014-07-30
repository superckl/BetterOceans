package me.superckl.betteroceans.client.gui;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.IModGuiFactory;

public class GuiFactoryBetterOceans implements IModGuiFactory{

	@Override
	public void initialize(final Minecraft minecraftInstance) {
		

	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return GuiConfigBetterOceans.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(
			final RuntimeOptionCategoryElement element) {
		
		return null;
	}

}
