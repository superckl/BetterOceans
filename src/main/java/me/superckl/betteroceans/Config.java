package me.superckl.betteroceans;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lombok.Getter;
import lombok.experimental.ExtensionMethod;
import me.superckl.betteroceans.reference.ModData;
import me.superckl.betteroceans.utility.LogHelper;
import me.superckl.betteroceans.utility.ReflectionUtil;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

@ExtensionMethod(LogHelper.class)
public class Config {

	@Getter
	private final Configuration configFile;
	
	@Getter
	private boolean genTrenches;
	@Getter
	private boolean genSeaweed;
	@Getter
	private boolean seaweedToWater;
	@Getter
	private int seaweedWaterBlockTries;
	@Getter
	private int seaweedTries;
	
	public Config(File config){
		this.configFile = new Configuration(config);
		try{
			this.configFile.load();
		}catch(Exception e){
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
			this.genSeaweed = this.configFile.getBoolean("Generate Seaweed", "world gen", true, "Generate seaweed in deeper water.");
			this.seaweedWaterBlockTries = this.configFile.getInt("Seaweed Water Block Attempts", "world gen", 20, 0, Integer.MAX_VALUE, "Defines how many attempts will be done to find a water block in a chunk when generating seaweed.");
			this.seaweedTries = this.configFile.getInt("Seaweed Tries", "world gen", 5, 0, Integer.MAX_VALUE, "Defines how many attempts will be made to place seaweed around a water block that was found.");
			this.seaweedToWater = this.configFile.getBoolean("Seaweed Breaks To Water", "general", true, "Determines whether seaweed is replaced by water or air when broken.");
			this.configFile.save();
			this.setConfigElements();
		}catch(Exception e){
			e.warn();
		}finally{
			if(this.configFile != null && this.configFile.hasChanged())
				this.configFile.save();
		}
	}
	
	@SubscribeEvent
	public void onConfigChange(OnConfigChangedEvent e){
		if(e.modID.equals(ModData.MOD_ID)){
			this.loadValues();
		}
	}
	
	public void setConfigElements(){
		List<IConfigElement> elements = new ArrayList<IConfigElement>();
		for(String cat:this.configFile.getCategoryNames()){
			elements.addAll(new ConfigElement(this.configFile.getCategory(cat)).getChildElements());
		}
		elements = Collections.unmodifiableList(elements);
		ReflectionUtil.setFinalStatic(ModData.class, "CONFIG_ELEMENTS", elements, true);
	}
	
}
