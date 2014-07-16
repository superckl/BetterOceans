package me.superckl.betteroceans.proxy;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.client.gui.GuiHandlerBetterOceans;
import me.superckl.betteroceans.common.entity.EntityWoodenBoat;
import me.superckl.betteroceans.common.entity.tile.TileEntityBasicBoatWorkbench;
import me.superckl.betteroceans.common.gen.SeaweedDecorator;
import me.superckl.betteroceans.common.gen.TrenchGenerator;
import me.superckl.betteroceans.common.handler.FuelHandler;
import me.superckl.betteroceans.common.handler.GenEventHandler;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;


public abstract class CommonProxy implements IProxy{

	@Override
	public void registerEntities(){
		EntityRegistry.registerModEntity(EntityWoodenBoat.class, "woodenBoat",
				EntityRegistry.findGlobalUniqueEntityId(), BetterOceans.getInstance(),
				80, 3, true);
		GameRegistry.registerTileEntity(TileEntityBasicBoatWorkbench.class, "tileEntityBasicBoatWorkbench");
	}

	@Override
	public void registerHandlers(){
		FMLCommonHandler.instance().bus().register(BetterOceans.getInstance().getConfig());
		MinecraftForge.TERRAIN_GEN_BUS.register(new GenEventHandler());
		GameRegistry.registerFuelHandler(new FuelHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(BetterOceans.getInstance(), new GuiHandlerBetterOceans());
	}

	@Override
	public void registerWorldGenerators(){
		GameRegistry.registerWorldGenerator(new SeaweedDecorator(), 100);
		GameRegistry.registerWorldGenerator(new TrenchGenerator(), 10);
	}

}
