package me.superckl.betteroceans.common.handler;

import me.superckl.betteroceans.common.gen.MapGenBetterOceansCaves;
import me.superckl.betteroceans.common.gen.MapGenBetterOceansRavine;
import me.superckl.betteroceans.common.gen.WorldGenBetterOceansClay;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GenEventHandler {

	@SubscribeEvent
	public void onInitMapGen(final InitMapGenEvent e){
		if(e.type == EventType.CAVE)
			e.newGen = new MapGenBetterOceansCaves();
		else if(e.type == EventType.RAVINE)
			e.newGen = new MapGenBetterOceansRavine();
	}

	@SubscribeEvent
	public void onCreateDecorator(final BiomeEvent.CreateDecorator e){
		e.newBiomeDecorator.clayGen = new WorldGenBetterOceansClay();
	}

}