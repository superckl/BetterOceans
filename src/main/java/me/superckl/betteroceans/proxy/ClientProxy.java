package me.superckl.betteroceans.proxy;

import me.superckl.betteroceans.client.handler.RenderTickHandler;
import me.superckl.betteroceans.client.render.block.RenderLantern;
import me.superckl.betteroceans.client.render.entity.RenderBOBoat;
import me.superckl.betteroceans.client.render.entity.RenderFishBasic;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.entity.EntityFishBasic;
import me.superckl.betteroceans.common.entity.tile.TileEntityLantern;
import me.superckl.betteroceans.common.reference.ModBlocks;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.RenderData;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy{

	@Override
	public void registerHandlers() {
		super.registerHandlers();
		MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
		MinecraftForge.EVENT_BUS.register(this);
		//FMLCommonHandler.instance().bus().register(rth);
		//MinecraftForge.EVENT_BUS.register(new GUIEventHandler());
	}

	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityBOBoat.class, new RenderBOBoat());
		RenderingRegistry.registerEntityRenderingHandler(EntityFishBasic.class, new RenderFishBasic());
		final RenderLantern r = new RenderLantern();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLantern.class, r);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lantern), r);
		//RenderData.softCoralID = RenderingRegistry.getNextAvailableRenderId();
		//RenderingRegistry.registerBlockHandler(RenderData.softCoralID, new RenderBlockSoftCoral());
		//MinecraftForgeClient.registerItemRenderer(ModItems.boatPart, new ItemRenderBoatPart());
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onTextureStich(final TextureStitchEvent.Pre e){
		if(e.map.getTextureType() != 1)
			return;
		RenderData.ANIMATED_GAUGE = e.map.registerIcon(ModData.MOD_ID+":icons/gaugeanimated");
	}

}
