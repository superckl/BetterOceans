package me.superckl.betteroceans;

import lombok.Getter;
import me.superckl.betteroceans.gen.BiomeGenBetterOceansDeepOcean;
import me.superckl.betteroceans.gen.BiomeGenBetterOceansOcean;
import me.superckl.betteroceans.gen.SeaweedDecorator;
import me.superckl.betteroceans.gen.TrenchGenerator;
import me.superckl.betteroceans.handler.FuelHandler;
import me.superckl.betteroceans.proxy.IProxy;
import me.superckl.betteroceans.reference.ModBlocks;
import me.superckl.betteroceans.reference.ModData;
import me.superckl.betteroceans.reference.ModItems;
import me.superckl.betteroceans.utility.LogHelper;
import me.superckl.betteroceans.utility.ReflectionUtil;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid=ModData.MOD_ID, name=ModData.MOD_NAME, version=ModData.VERSION, guiFactory = ModData.GUI_FACTORY)
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
		ModItems.init();
		ModBlocks.init();
		GameRegistry.registerWorldGenerator(new SeaweedDecorator(), 100);
		GameRegistry.registerWorldGenerator(new TrenchGenerator(), 10);
	}

	@EventHandler
	public void init(final FMLInitializationEvent e){
		FMLCommonHandler.instance().bus().register(this.config);
		BetterOceans.proxy.registerTickHandlers();
		GameRegistry.registerFuelHandler(new FuelHandler());
		ModItems.overrideRecipes();
		LogHelper.info("Replacing ocean biomes...");
		final BiomeGenBetterOceansOcean boO = new BiomeGenBetterOceansOcean();
		final BiomeGenBetterOceansDeepOcean boDO = new BiomeGenBetterOceansDeepOcean();
		if(!ReflectionUtil.setFinalStatic(BiomeGenBase.class, "ocean", boO, true))
			LogHelper.fatal("Failed to override ocean biome! Loading worlds generated with Better Oceans may cause corruption!");
		if(!ReflectionUtil.setFinalStatic(BiomeGenBase.class, "deepOcean", boDO, true))
			LogHelper.fatal("Failed to override deep ocean biome! Loading worlds generated with Better Oceans may cause corruption!");
	}

	@EventHandler
	public void postInit(final FMLPostInitializationEvent e){

	}

}
