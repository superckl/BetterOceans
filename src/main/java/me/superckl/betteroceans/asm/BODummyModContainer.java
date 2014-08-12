package me.superckl.betteroceans.asm;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class BODummyModContainer extends DummyModContainer{

	public static final Logger logger = LogManager.getLogger("BetterOceansCore");

	public BODummyModContainer() {

		super(new ModMetadata());
		final ModMetadata meta = this.getMetadata();
		meta.modId = "BetterOceansCore";
		meta.name = "Better Oceans Core";
		meta.parent = "BetterOceans";
		meta.version = "1.0";
		meta.authorList = Arrays.asList("superckl");
		meta.description = "";
		meta.url = "https://github.com/superckl/betteroceans";
		meta.updateUrl = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";
	}

	@Override
	public boolean registerBus(final EventBus bus, final LoadController controller) {
		bus.register(this);
		return true;
	}

	@Subscribe
	public void modConstruction(final FMLConstructionEvent evt){

	}

	@Subscribe
	public void preInit(final FMLPreInitializationEvent evt) {

	}

	@Subscribe
	public void init(final FMLInitializationEvent evt) {

	}


	@Subscribe
	public void postInit(final FMLPostInitializationEvent evt) {

	}

}
