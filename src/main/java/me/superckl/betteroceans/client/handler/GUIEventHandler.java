package me.superckl.betteroceans.client.handler;

import java.util.Random;

import me.superckl.betteroceans.client.gui.BOGuiConfig;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.utility.BOReflectionHelper;
import me.superckl.betteroceans.common.utility.LogHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

//Not using
public class GUIEventHandler {

	private static final ResourceLocation[] textures = new ResourceLocation[] {
		new ResourceLocation(ModData.MOD_ID+":textures/blocks/saltwater_still.png"),
		new ResourceLocation(ModData.MOD_ID+":textures/blocks/saltwater_flow.png"),
		new ResourceLocation(ModData.MOD_ID+":textures/blocks/basicbenchside.png"),
		new ResourceLocation(ModData.MOD_ID+":textures/blocks/basicbenchtop.png"),
		new ResourceLocation(ModData.MOD_ID+":textures/blocks/seaweed1.png")
	};

	private static final Random random = new Random();
	private ResourceLocation previous;
	private boolean isOpen;

	@SubscribeEvent
	public void onGUIOpen(final GuiOpenEvent e){
		LogHelper.info("new gui");
		if(e.gui instanceof BOGuiConfig){
			if(!this.isOpen){
				this.previous = Gui.optionsBackground;
				LogHelper.info("Setting prev "+this.previous.getResourcePath());
			}
			this.isOpen = true;
			LogHelper.info("setting new texture");
			BOReflectionHelper.setPrivateFinalValue(Gui.class, null, GUIEventHandler.textures[GUIEventHandler.random.nextInt(GUIEventHandler.textures.length)], "optionsBackground", "field_110325_k");
		}else if(this.isOpen){
			this.isOpen = false;
			LogHelper.info("restoring "+this.previous.getResourcePath());
			BOReflectionHelper.setPrivateFinalValue(Gui.class, null, this.previous, "optionsBackground", "field_110325_k");
		}
	}

}
