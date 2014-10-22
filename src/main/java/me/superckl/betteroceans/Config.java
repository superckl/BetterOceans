package me.superckl.betteroceans;

import java.io.File;

import lombok.Getter;
import me.superckl.betteroceans.common.reference.ModData;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Config {

	public static abstract class Category{
		public static final String WORLD_GEN = "world gen";
		public static final String GENERAL = "general";
		public static final String BIOMES_O_PLENTY = "biomes o plenty";
		public static final String STAMINA = "stamina";
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
	private boolean forceOverride;
	@Getter
	private boolean removeSubbiomes;
	@Getter
	private boolean seaweedOrKelp;
	@Getter
	private boolean fluidReplace;
	@Getter
	private int seaweedTries;
	@Getter
	private int seaweedWaterBlockTries;
	@Getter
	private float oceanBaseHeight;
	@Getter
	private float oceanHeightVariation;
	@Getter
	private float deepOceanBaseHeight;
	@Getter
	private float deepOceanHeightVariation;
	@Getter
	private boolean genReefs;
	@Getter
	private boolean debugTooltips;
	@Getter
	private boolean disableStamina;
	@Getter
	private float staminaDrain;
	@Getter
	private float lifejacketModifier;
	@Getter
	private float regenRate;
	@Getter
	private float airUseRate;

	public Config(final File config){
		this.configFile = new Configuration(config);
		try{
			this.configFile.load();
		}catch(final Exception e){
			e.printStackTrace();
		}finally{
			if(this.configFile.hasChanged())
				this.configFile.save();
		}
	}

	public void loadValues(){
		try{
			this.genTrenches = this.configFile.getBoolean("Generate Trenches", Category.WORLD_GEN, true, "Generate trenches in oceans.");
			this.genReefs = this.configFile.getBoolean("Generate Reefs", Category.WORLD_GEN, true, "Generate reefs in oceans.");
			this.overrideOcean = this.configFile.getBoolean("Override Oceans", Category.WORLD_GEN, true, "Replace Vanilla oceans with Better Oceans oceans.");
			this.forceOverride = this.configFile.getBoolean("Force Override", Category.WORLD_GEN, false, "If true, Better Oceans will re-override oceans in post-init if another mod has overriden them since the first override.");
			this.genSeaweed = this.configFile.getBoolean("Generate Seaweed", Category.WORLD_GEN, true, "Generate seaweed in deeper water.");
			this.seaweedWaterBlockTries = this.configFile.getInt("Seaweed Water Block Attempts", Category.WORLD_GEN, 20, 0, Integer.MAX_VALUE, "Defines how many attempts will be done to find a water block in a chunk when generating seaweed.");
			this.seaweedTries = this.configFile.getInt("Seaweed Tries", Category.WORLD_GEN, 3, 0, Integer.MAX_VALUE, "Defines how many attempts will be made to place seaweed around a water block that was found.");
			this.otherDecoration = this.configFile.getBoolean("Other Mod Ocean Decorations", Category.WORLD_GEN, true, "Allows other mods to generate decorations in oceans.");
			this.oceanBaseHeight = (float) this.configFile.get("Ocean Base Height", Category.WORLD_GEN, -1.2F, "Defines the base height for oceans.", -2F, 2F).getDouble();
			this.deepOceanBaseHeight = (float) this.configFile.get("Deep Ocean Base Height", Category.WORLD_GEN, -1.6F, "Defines the base height for deep oceans.", -2F, 2F).getDouble();
			this.oceanHeightVariation = (float) this.configFile.get("Ocean Height Variation", Category.WORLD_GEN, 0.18F, "Defines the height variation for oceans.", 0.0F, 2F).getDouble();
			this.deepOceanHeightVariation = (float) this.configFile.get("Deep Ocean Height Variation", Category.WORLD_GEN, 0.29F, "Defines the height variation for deep oceans.", 0.0F, 2F).getDouble();

			this.infiniteSaltwater = this.configFile.getBoolean("Infinite Saltwater", Category.GENERAL, true, "If true, saltwater will emulate vanilla infinite water mechanics.");
			this.fluidReplace = this.configFile.getBoolean("Replace Aquatic Plants With Fluid", Category.GENERAL, true, "Determines whether aquatic plants are replaced by (salt) water or air when broken.");
			this.debugTooltips = this.configFile.getBoolean("Tooltip Debugging", Category.GENERAL, false, "Determines if superfluous information will be displayed in some tooltips.");
			this.airUseRate = this.configFile.getFloat("Air Tank Drain Rate",Category.GENERAL, .000083F, 0F, 100F, "Determines how fast a scuba tank will drain. Tanks can hold up to 100 units of air, and this value will be drained every tick.");

			this.disableStamina = this.configFile.getBoolean("Disable Swim Stamina", Category.STAMINA, false, "If true, players will be able to swim as in vanilla minecraft.");
			this.staminaDrain = this.configFile.getFloat("Base Stamina Drain", Category.STAMINA, 2F, 0F, 100F, "Determines how fast stamina drains.");
			this.lifejacketModifier = this.configFile.getFloat("Life Jacket Drain Modifier", Category.STAMINA, 0.5F, 0F, 1F, "Determines how much the life jacket affects stamina drain. The default value (0.5) cuts the drain in half.");
			this.regenRate = this.configFile.getFloat("Stamina Regen Rate", Category.STAMINA, 1F, 0F, 100F, "Determines how fast stamina regens.");

			this.seaweedOrKelp = this.configFile.getBoolean("Seaweed or Kelp", Category.BIOMES_O_PLENTY, true, "Allows players to use seaweed where kelp is required in recipes.");
			this.removeSubbiomes = this.configFile.getBoolean("Remove SubBiomes", Category.BIOMES_O_PLENTY, true, "Remove Biomes O' Plenty's ocean subbiomes such as Coral Reef and Kelp Forest.");

			this.configFile.save();
		}catch(final Exception e){
			e.printStackTrace();
		}finally{
			if(this.configFile.hasChanged())
				this.configFile.save();
		}
	}

	@SubscribeEvent
	public void onConfigChange(final OnConfigChangedEvent e){
		if(e.modID.equals(ModData.MOD_ID))
			this.loadValues();
	}

}
