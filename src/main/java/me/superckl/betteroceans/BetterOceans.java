package me.superckl.betteroceans;

import lombok.Getter;
import me.superckl.betteroceans.common.gen.BiomeGenBetterOceansDeepOcean;
import me.superckl.betteroceans.common.gen.BiomeGenBetterOceansOcean;
import me.superckl.betteroceans.common.gen.SeaweedDecorator;
import me.superckl.betteroceans.common.gen.TrenchGenerator;
import me.superckl.betteroceans.common.handler.FuelHandler;
import me.superckl.betteroceans.common.handler.GenEventHandler;
import me.superckl.betteroceans.common.reference.ModBlocks;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.ReflectionUtil;
import me.superckl.betteroceans.proxy.IProxy;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraftforge.common.MinecraftForge;
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
		GameRegistry.registerWorldGenerator(new SeaweedDecorator(), 500);
		GameRegistry.registerWorldGenerator(new TrenchGenerator(), 10);
	}

	@EventHandler
	public void init(final FMLInitializationEvent e){
		FMLCommonHandler.instance().bus().register(this.config);
		MinecraftForge.TERRAIN_GEN_BUS.register(new GenEventHandler());
		BetterOceans.proxy.registerTickHandlers();
		GameRegistry.registerFuelHandler(new FuelHandler());
		ModItems.overrideRecipes();
		LogHelper.info("Replacing ocean biomes...");
		final BiomeGenBetterOceansOcean boO = new BiomeGenBetterOceansOcean();
		new BiomeGenBetterOceansDeepOcean();
		if(!this.config.isOverrideOcean())
			LogHelper.warn("Ocean overriding is disabled! Loading worlds that were generated with this enabled may be unstable!");
		else{
			//Field oceanField = ReflectionUtil.findBiomeGenField(BiomeGenBase.ocean.biomeID);
			if(!ReflectionUtil.setFinalStatic(BiomeGenBase.class, boO, true, "ocean", "field_76771_b"))
				LogHelper.fatal("Failed to override ocean biome! Loading worlds generated with Better Oceans may have unpredictable results!");
			//Field deepOceanField = ReflectionUtil.findBiomeGenField(BiomeGenBase.deepOcean.biomeID);
			if(!ReflectionUtil.setFinalStatic(BiomeGenBase.class, boO, true, "deepOcean", "field_150575_M"))
				LogHelper.fatal("Failed to override deep ocean biome! Loading worlds generated with Better Oceans may have unpredictable results!");
		}
		WorldGenClay.class.getCanonicalName();
	}

	@EventHandler
	public void postInit(final FMLPostInitializationEvent e){

	}

}
