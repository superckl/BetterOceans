package me.superckl.betteroceans;

import lombok.Getter;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="BetterOceans", name="Better Oceans", version="1.0")
public class BetterOceans {
	
	@Instance("BetterOceans")
	@Getter
	private static BetterOceans instance;
	
	@Getter
	private Config config;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		this.config = new Config();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e){
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e){
		
	}
	
}
