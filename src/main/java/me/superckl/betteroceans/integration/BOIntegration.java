package me.superckl.betteroceans.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;
import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.gen.BiomeGenBetterDeepOcean;
import me.superckl.betteroceans.common.gen.BiomeGenBetterOcean;
import me.superckl.betteroceans.common.utility.BOReflectionHelper;
import me.superckl.betteroceans.common.utility.BiomeHelper;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.StringHelper;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState;

public class BOIntegration implements IIntegrationModule{

	public static final BOIntegration INSTANCE = new BOIntegration();
	private static final Map<List<String>, String> modules = new HashMap<List<String>, String>();

	static{
		BOIntegration.modules.put(Arrays.asList(new String[] {"BiomesOPlenty"}), "me.superckl.betteroceans.integration.bop.BiomesOPlentyIntegration");
		BOIntegration.modules.put(Arrays.asList(new String[] {"NotEnoughItems"}), "me.superckl.betteroceans.integration.nei.NotEnoughItemsIntegration");
		BOIntegration.modules.put(Arrays.asList(new String[] {"Mariculture"}), "me.superckl.betteroceans.integration.mariculture.MaricultureIntegration");
	}

	@Getter
	private final List<IIntegrationModule> activeModules = new ArrayList<IIntegrationModule>();

	private BOIntegration() {}

	@Override
	public void preInit(){
		boolean noGo = false;
		for(final Entry<List<String>, String> entry:BOIntegration.modules.entrySet()){
			for(final String mod:entry.getKey())
				if(!Loader.isModLoaded(mod)){
					noGo = true;
					break;
				}
			if(!noGo)
				try {
					final IIntegrationModule module = (IIntegrationModule) Class.forName(entry.getValue()).newInstance();
					this.activeModules.add(module);
					LogHelper.info(StringHelper.build("Enabled ", module.getName(), " module."));
				} catch (final Exception e) {
					LogHelper.error("Failed to instantiate integration module "+entry.getValue());
					e.printStackTrace();
				}
		}
		if(!Loader.instance().isInState(LoaderState.PREINITIALIZATION))
			LogHelper.error("Class "+BOReflectionHelper.retrieveCallingStackTraceElement().getClassName()+" attempted to preinitialize integration, but FML is not in that state!");
		for(final IIntegrationModule module:this.activeModules)
			module.preInit();
	}


	@Override
	public void init() {
		if(!Loader.instance().isInState(LoaderState.INITIALIZATION))
			LogHelper.error("Class "+BOReflectionHelper.retrieveCallingStackTraceElement().getClassName()+" attempted to initialize integration, but FML is not in that state!");
		for(final IIntegrationModule module:this.activeModules)
			module.init();
	}

	@Override
	public void postInit(){
		if(!Loader.instance().isInState(LoaderState.POSTINITIALIZATION))
			LogHelper.error("Class "+BOReflectionHelper.retrieveCallingStackTraceElement().getClassName()+" attempted to postinitialize integration, but FML is not in that state!");
		for(final IIntegrationModule module:this.activeModules)
			module.postInit();

		//Ensure oceans are still overriden...
		if(BetterOceans.getInstance().getConfig().isOverrideOcean() && BetterOceans.getInstance().getConfig().isForceOverride() && (!(BiomeGenBase.getBiomeGenArray()[BiomeGenBase.ocean.biomeID] instanceof BiomeGenBetterOcean) ||
				!(BiomeGenBase.getBiomeGenArray()[BiomeGenBase.deepOcean.biomeID] instanceof BiomeGenBetterDeepOcean))){
			LogHelper.warn("Something else overrode oceans since Init (or overriding them failed)! You should probably report this (Include a list of possible mods, thanks)... Re-overriding...");
			BiomeHelper.replaceOceanBiomes();
		}
	}

	@Override
	public String getName() {
		return "Better Oceans Integration Manager";
	}

}
