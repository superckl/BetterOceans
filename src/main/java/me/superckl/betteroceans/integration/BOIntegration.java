package me.superckl.betteroceans.integration;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.gen.BiomeGenBetterDeepOcean;
import me.superckl.betteroceans.common.gen.BiomeGenBetterOcean;
import me.superckl.betteroceans.common.utility.BOReflectionHelper;
import me.superckl.betteroceans.common.utility.BiomeHelper;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.integration.bop.BiomesOPlentyIntegration;
import me.superckl.betteroceans.integration.nei.NotEnoughItemsIntegration;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState;

public class BOIntegration implements IIntegrationModule{

	public static final BOIntegration INSTANCE = new BOIntegration();

	private BOIntegration() {}

	private final IIntegrationModule[] modules = new IIntegrationModule[] {new BiomesOPlentyIntegration(), new NotEnoughItemsIntegration()/*, new DynamicLightsIntegration()*/};

	@Override
	public void preInit(){
		if(!Loader.instance().isInState(LoaderState.PREINITIALIZATION))
			LogHelper.error("Class "+BOReflectionHelper.retrieveCallingStackTraceElement().getClassName()+" attempted to preinitialize integration, but FML is not in that state!");
		for(final IIntegrationModule module:this.modules){
			for(final String mod:module.getRequiredMods())
				if(!Loader.isModLoaded(mod))
					break;
			module.preInit();
		}
	}


	@Override
	public void init() {
		if(!Loader.instance().isInState(LoaderState.INITIALIZATION))
			LogHelper.error("Class "+BOReflectionHelper.retrieveCallingStackTraceElement().getClassName()+" attempted to initialize integration, but FML is not in that state!");
		for(final IIntegrationModule module:this.modules){
			for(final String mod:module.getRequiredMods())
				if(!Loader.isModLoaded(mod))
					break;
			module.init();
		}
	}

	@Override
	public void postInit(){
		if(!Loader.instance().isInState(LoaderState.POSTINITIALIZATION))
			LogHelper.error("Class "+BOReflectionHelper.retrieveCallingStackTraceElement().getClassName()+" attempted to postinitialize integration, but FML is not in that state!");
		for(final IIntegrationModule module:this.modules){
			for(final String mod:module.getRequiredMods())
				if(!Loader.isModLoaded(mod))
					break;
			module.postInit();
		}

		//Ensure oceans are still overriden...
		if(BetterOceans.getInstance().getConfig().isOverrideOcean() && BetterOceans.getInstance().getConfig().isForceOverride() && (!(BiomeGenBase.getBiomeGenArray()[BiomeGenBase.ocean.biomeID] instanceof BiomeGenBetterOcean) ||
				!(BiomeGenBase.getBiomeGenArray()[BiomeGenBase.deepOcean.biomeID] instanceof BiomeGenBetterDeepOcean))){
			LogHelper.warn("Something else overrode oceans since Init (or overriding them failed)! You should probably report this (Include a list of possible mods, thanks)... Re-overriding...");
			BiomeHelper.replaceOceanBiomes();
		}
	}

	@Override
	public String[] getRequiredMods() {
		return new String[0];
	}


}
