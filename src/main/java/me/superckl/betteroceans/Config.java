package me.superckl.betteroceans;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.experimental.ExtensionMethod;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.ReflectionHelper;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@ExtensionMethod(LogHelper.class)
public class Config {

	@Getter
	private final Configuration configFile;
	@Getter
	private boolean genTrenches;
	@Getter
	private boolean genSeaweed;
	@Getter
	private boolean overrideOcean;
	@Getter
	private boolean seaweedToWater;
	@Getter
	private int seaweedWaterBlockTries;
	@Getter
	private int seaweedTries;
	@Getter
	private boolean otherDecoration;
	@Getter
	private boolean seaweedOrKelp;

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
			throw new IllegalStateException("Config file is null!");
		try{
			this.genTrenches = this.configFile.getBoolean("Generate Trenches", "world gen", true, "Generate trenches in oceans.");
			this.overrideOcean = this.configFile.getBoolean("Override Oceans", "world gen", true, "Replace Vanilla oceans with Better Oceans oceans.");
			this.genSeaweed = this.configFile.getBoolean("Generate Seaweed", "world gen", true, "Generate seaweed in deeper water.");
			this.seaweedWaterBlockTries = this.configFile.getInt("Seaweed Water Block Attempts", "world gen", 20, 0, Integer.MAX_VALUE, "Defines how many attempts will be done to find a water block in a chunk when generating seaweed.");
			this.seaweedTries = this.configFile.getInt("Seaweed Tries", "world gen", 3, 0, Integer.MAX_VALUE, "Defines how many attempts will be made to place seaweed around a water block that was found.");
			this.seaweedToWater = this.configFile.getBoolean("Seaweed Breaks To Water", "general", true, "Determines whether seaweed is replaced by water or air when broken.");
			this.otherDecoration = this.configFile.getBoolean("Other Mod Ocean Decorations", "world gen", true, "Aloow other mods to generate decorations in oceans.");
			this.seaweedOrKelp = this.configFile.getBoolean("Seaweed or Kelp", "biomes o plenty", true, "Allows players to use seaweed where kelp is required in recipes.");
			this.configFile.save();
			this.setConfigElements();
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

	public void setConfigElements(){
		List<IConfigElement> elements = new ArrayList<IConfigElement>();
		for(final String cat:this.configFile.getCategoryNames())
			elements.addAll(new ConfigElement(this.configFile.getCategory(cat)).getChildElements());
		elements = Collections.unmodifiableList(elements);
		ReflectionHelper.setFinalStatic(ModData.class, elements, true, "CONFIG_ELEMENTS");
	}

}
