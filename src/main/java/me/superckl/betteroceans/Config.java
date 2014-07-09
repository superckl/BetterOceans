package me.superckl.betteroceans;

import java.io.File;

import lombok.Getter;
import lombok.experimental.ExtensionMethod;
import me.superckl.betteroceans.utility.LogHelper;
import net.minecraftforge.common.config.Configuration;

@ExtensionMethod(LogHelper.class)
public class Config {

	@Getter
	private final Configuration configFile;
	
	public Config(File config){
		this.configFile = new Configuration(config);
		try{
			this.configFile.load();
		}catch(Exception e){
			e.warn();
		}finally{
			this.configFile.save();
		}
	}
	
}
