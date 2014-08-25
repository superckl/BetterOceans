package me.superckl.betteroceans.proxy;

import java.awt.Color;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.client.gui.GuiHandlerBetterOceans;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.entity.EntityFishBasic;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import me.superckl.betteroceans.common.gen.WorldGeneratorReef;
import me.superckl.betteroceans.common.gen.WorldGeneratorSeaweed;
import me.superckl.betteroceans.common.gen.WorldGeneratorTrench;
import me.superckl.betteroceans.common.handler.BucketEventHandler;
import me.superckl.betteroceans.common.handler.EntityEventHandler;
import me.superckl.betteroceans.common.handler.FuelHandler;
import me.superckl.betteroceans.common.handler.GenEventHandler;
import me.superckl.betteroceans.common.handler.PlayerTickHandler;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.reference.NetworkData;
import me.superckl.betteroceans.common.utility.BOReflectionHelper;
import me.superckl.betteroceans.network.MessagePartUpdate;
import me.superckl.betteroceans.network.MessagePartUpdateHandler;
import me.superckl.betteroceans.network.MessagePressCraft;
import me.superckl.betteroceans.network.MessagePressCraftHandler;
import me.superckl.betteroceans.network.MessageSelectBoatPart;
import me.superckl.betteroceans.network.MessageSelectPartHandler;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;


public abstract class CommonProxy implements IProxy{

	@Override
	public void registerEntities(){
		final int boatID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityBOBoat.class, "bobetterboat", boatID);
		EntityRegistry.registerModEntity(EntityBOBoat.class, "bobetterboat", boatID, BetterOceans.getInstance(), 80, 3, true);

		final int fishID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityFishBasic.class, "bofishbasic", fishID, Color.BLUE.getRGB(), Color.BLACK.getRGB());
		EntityRegistry.registerModEntity(EntityFishBasic.class, "bofishbasic", fishID, BetterOceans.getInstance(), 80, 3, true);
		EntityRegistry.instance();
		EntityRegistry.addSpawn(EntityFishBasic.class, 12, 1, 10, EnumCreatureType.waterCreature, BiomeGenBase.ocean, BiomeGenBase.deepOcean, BiomeGenBase.river);

		GameRegistry.registerTileEntity(TileEntityBoatbench.class, "tileEntityBasicBoatWorkbench");
	}

	@Override
	public void registerHandlers(){
		FMLCommonHandler.instance().bus().register(BetterOceans.getInstance().getConfig());
		MinecraftForge.TERRAIN_GEN_BUS.register(new GenEventHandler());
		MinecraftForge.EVENT_BUS.register(ModItems.boatPart);
		MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
		MinecraftForge.EVENT_BUS.register(new BucketEventHandler());
		GameRegistry.registerFuelHandler(new FuelHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(BetterOceans.getInstance(), new GuiHandlerBetterOceans());
		FMLCommonHandler.instance().bus().register(new PlayerTickHandler());
		//FMLCommonHandler.instance().bus().register(new ServerTickHandler());
	}

	@Override
	public void registerWorldGenerators(){
		GameRegistry.registerWorldGenerator(new WorldGeneratorSeaweed(), 100);
		GameRegistry.registerWorldGenerator(new WorldGeneratorReef(), 50);
		GameRegistry.registerWorldGenerator(new WorldGeneratorTrench(), 10);
	}

	@Override
	public void registerNetworkHandlers(){
		BOReflectionHelper.setPrivateFinalValue(NetworkData.class, null, NetworkRegistry.INSTANCE.newSimpleChannel(NetworkData.PART_SELECT_CHANNEL_NAME),
				"PART_SELECT_CHANNEL");
		NetworkData.PART_SELECT_CHANNEL.registerMessage(MessageSelectPartHandler.class,
				MessageSelectBoatPart.class, 0, Side.SERVER);
		BOReflectionHelper.setPrivateFinalValue(NetworkData.class, null, NetworkRegistry.INSTANCE.newSimpleChannel(NetworkData.UPDATE_PARTS_CHANNEL_NAME),
				"UPDATE_PARTS_CHANNEL");
		NetworkData.UPDATE_PARTS_CHANNEL.registerMessage(MessagePartUpdateHandler.class, MessagePartUpdate.class, 0, Side.CLIENT);
		BOReflectionHelper.setPrivateFinalValue(NetworkData.class, null, NetworkRegistry.INSTANCE.newSimpleChannel(NetworkData.CRAFT_PRESS_CHANNEL_NAME), "CRAFT_PRESS_CHANNEL");
		NetworkData.CRAFT_PRESS_CHANNEL.registerMessage(MessagePressCraftHandler.class, MessagePressCraft.class, 0, Side.SERVER);
	}

}
