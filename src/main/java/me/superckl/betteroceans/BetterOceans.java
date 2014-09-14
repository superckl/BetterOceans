package me.superckl.betteroceans;

import lombok.Getter;
import me.superckl.betteroceans.common.reference.BoatParts;
import me.superckl.betteroceans.common.reference.ModBlocks;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModFluids;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.reference.RecipeRegistry;
import me.superckl.betteroceans.common.utility.BiomeHelper;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.integration.BOIntegration;
import me.superckl.betteroceans.proxy.IProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=ModData.MOD_ID, name=ModData.MOD_NAME, version=ModData.VERSION, guiFactory = ModData.GUI_FACTORY, dependencies = "after:BiomesOPlenty; after:NotEnoughItems")
public class BetterOceans {

	@Instance(ModData.MOD_ID)
	@Getter
	private static BetterOceans instance;

	@SidedProxy(clientSide=ModData.CLIENT_PROXY, serverSide=ModData.SERVER_PROXY)
	@Getter
	private static IProxy proxy;

	@Getter
	private Config config;

	@EventHandler
	public void preInit(final FMLPreInitializationEvent e){
		this.config = new Config(e.getSuggestedConfigurationFile());
		this.config.loadValues();
		BetterOceans.proxy.registerNetworkHandlers();
		ModFluids.init();
		ModBlocks.init();
		ModItems.init();
		RecipeRegistry.registerBaseRecipes();
		BetterOceans.proxy.registerWorldGenerators();
		BetterOceans.proxy.registerEntities();
		BetterOceans.proxy.registerRenderers();
		BOIntegration.INSTANCE.preInit();
		try {
			Class.forName(BoatParts.class.getCanonicalName());//Let's just make sure all the parts get loaded;
		} catch (final ClassNotFoundException e1) {
			//This shouldn't happen
		}
	}

	@EventHandler
	public void init(final FMLInitializationEvent e){
		BetterOceans.proxy.registerHandlers();
		ModItems.overrideItems();
		LogHelper.debug("Replacing ocean biomes...");
		BiomeHelper.replaceOceanBiomes();

		FMLInterModComms.sendMessage("Waila", "register", "me.superckl.betteroceans.integration.waila.BOWailaDataProvider.callbackRegister");
		FMLInterModComms.sendMessage("Waila", "register", "me.superckl.betteroceans.integration.waila.BOWailaEntityProvider.callbackRegister");
		BOIntegration.INSTANCE.init();
	}

	@EventHandler
	public void postInit(final FMLPostInitializationEvent e){
		BOIntegration.INSTANCE.postInit();
	}

}
