package me.superckl.betteroceans;

import java.io.File;

import lombok.Getter;
import lombok.experimental.ExtensionMethod;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.utility.LogHelper;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@ExtensionMethod(LogHelper.class)
public class Config {

	public static abstract class Category{
		public static final String WORLD_GEN = "world gen";
		public static final String GENERAL = "general";
		public static final String BIOMES_O_PLENTY = "biomes o plenty";
	}

	@Getter
	private final Configuration configFile;
	@Getter
	private boolean genSeaweed;
	@Getter
	private boolean genTrenches;
	@Getter
	private boolean infiniteSaltwater;
	@Getter
	private boolean otherDecoration;
	@Getter
	private boolean overrideOcean;
	@Getter
	private boolean removeSubbiomes;
	@Getter
	private boolean seaweedOrKelp;
	@Getter
	private boolean seaweedToWater;
	@Getter
	private int seaweedTries;
	@Getter
	private int seaweedWaterBlockTries;
	@Getter
	private boolean genReefs;

	public Config(final File config){
		this.configFile = new Configuration(config);
		try{
			this.configFile.load();
		}catch(final Exception e){
			e.warn();
		}finally{
			if(this.configFile != null && this.configFile.hasChanged())
				this.configFile.save();
		}
	}

	public void loadValues(){
		if(this.configFile == null)
			throw new IllegalStateException("Config file is null! Things are not going to go well!");
		try{
			this.genTrenches = this.configFile.getBoolean("Generate Trenches", Category.WORLD_GEN, true, "Generate trenches in oceans.");
			this.genReefs = this.configFile.getBoolean("Generate Reefs", Category.WORLD_GEN, true, "Generate reefs in oceans.");
			this.overrideOcean = this.configFile.getBoolean("Override Oceans", Category.WORLD_GEN, true, "Replace Vanilla oceans with Better Oceans oceans.");
			this.genSeaweed = this.configFile.getBoolean("Generate Seaweed", Category.WORLD_GEN, true, "Generate seaweed in deeper water.");
			this.seaweedWaterBlockTries = this.configFile.getInt("Seaweed Water Block Attempts", Category.WORLD_GEN, 20, 0, Integer.MAX_VALUE, "Defines how many attempts will be done to find a water block in a chunk when generating seaweed.");
			this.seaweedTries = this.configFile.getInt("Seaweed Tries", Category.WORLD_GEN, 3, 0, Integer.MAX_VALUE, "Defines how many attempts will be made to place seaweed around a water block that was found.");
			this.otherDecoration = this.configFile.getBoolean("Other Mod Ocean Decorations", Category.WORLD_GEN, true, "Allows other mods to generate decorations in oceans.");

			this.infiniteSaltwater = this.configFile.getBoolean("Infinite Saltwater", Category.GENERAL, true, "If true, saltwater will emulate vanilla infinite water mechanics.");
			this.seaweedToWater = this.configFile.getBoolean("Seaweed Breaks To Water", Category.GENERAL, true, "Determines whether seaweed is replaced by water or air when broken.");

			this.seaweedOrKelp = this.configFile.getBoolean("Seaweed or Kelp", Category.BIOMES_O_PLENTY, true, "Allows players to use seaweed where kelp is required in recipes.");
			this.removeSubbiomes = this.configFile.getBoolean("Remove SubBiomes", Category.BIOMES_O_PLENTY, true, "Remove Biomes O' Plenty's ocean subbiomes such as Coral Reef and Kelp Forest.");

			this.configFile.save();
		}catch(final Exception e){
			e.warn();
		}finally{
			if(this.configFile != null && this.configFile.hasChanged())
				this.configFile.save();
		}
	}

	@SubscribeEvent
	public void onConfigChange(final OnConfigChangedEvent e){
		if(e.modID.equals(ModData.MOD_ID))
			this.loadValues();
	}

}
