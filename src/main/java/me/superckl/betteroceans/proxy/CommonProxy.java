package me.superckl.betteroceans.proxy;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.client.gui.GuiHandlerBetterOceans;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatWorkbench;
import me.superckl.betteroceans.common.gen.WorldGeneratorSeaweed;
import me.superckl.betteroceans.common.gen.WorldGeneratorTrench;
import me.superckl.betteroceans.common.handler.FuelHandler;
import me.superckl.betteroceans.common.handler.GenEventHandler;
import me.superckl.betteroceans.common.reference.NetworkData;
import me.superckl.betteroceans.common.utility.ReflectionHelper;
import me.superckl.betteroceans.network.MessageHandler;
import me.superckl.betteroceans.network.MessageSelectBoat;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;


public abstract class CommonProxy implements IProxy{

	@Override
	public void registerEntities(){
		EntityRegistry.registerModEntity(EntityBOBoat.class, "woodenBoat",
				EntityRegistry.findGlobalUniqueEntityId(), BetterOceans.getInstance(),
				80, 3, true);
		GameRegistry.registerTileEntity(TileEntityBoatWorkbench.class, "tileEntityBasicBoatWorkbench");
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
		GameRegistry.registerWorldGenerator(new WorldGeneratorSeaweed(), 100);
		GameRegistry.registerWorldGenerator(new WorldGeneratorTrench(), 10);
	}

	@Override
	public void registerNetworkHandlers(){
		ReflectionHelper.setFinalStatic(NetworkData.class, NetworkRegistry.INSTANCE.newSimpleChannel(NetworkData.BOAT_SELECT_CHANNEL_NAME),
				true, "BOAT_SELECT_CHANNEL");
		NetworkData.BOAT_SELECT_CHANNEL.registerMessage(MessageHandler.class,
				MessageSelectBoat.class, 0, Side.SERVER);
	}

}
