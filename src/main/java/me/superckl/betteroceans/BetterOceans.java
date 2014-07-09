package me.superckl.betteroceans;

import lombok.Getter;
import me.superckl.betteroceans.proxy.IProxy;
import me.superckl.betteroceans.reference.ModData;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=ModData.MOD_ID, name=ModData.MOD_NAME, version=ModData.VERSION)
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
	public void preInit(FMLPreInitializationEvent e){
		this.config = new Config(e.getSuggestedConfigurationFile());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e){
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e){
		
	}
	
}
