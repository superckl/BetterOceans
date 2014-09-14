package me.superckl.betteroceans.integration.dynl;

import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.integration.IIntegrationModule;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import atomicstryker.dynamiclights.client.DynamicLights;
import atomicstryker.dynamiclights.client.IDynamicLightSource;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DynamicLightsIntegration implements IIntegrationModule{

	@SideOnly(Side.CLIENT)
	private  boolean enabled;
	@SideOnly(Side.CLIENT)
	private IDynamicLightSource lumPowderSource;
	@SideOnly(Side.CLIENT)
	private Minecraft mc;

	@Override
	public void preInit() {}

	@Override
	public void init() {
		FMLCommonHandler.instance().bus().register(this);
	}

	@Override
	public void postInit() {}

	@Override
	public String[] getRequiredMods() {
		return new String[] {"DynamicLights"};
	}

	@SubscribeEvent
	public void onClientTick(final TickEvent.ClientTickEvent e){
		if(this.mc == null)
			this.mc = Minecraft.getMinecraft();
		if(this.mc.thePlayer != null && this.mc.theWorld != null){
			if(this.lumPowderSource == null)
				this.lumPowderSource = new IDynamicLightSource() {

				@Override
				public int getLightLevel() {
					return 8;
				}

				@Override
				public Entity getAttachmentEntity() {
					return DynamicLightsIntegration.this.mc.thePlayer;
				}
			};
			if(this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() == ModItems.lumPowder && !this.enabled){
				DynamicLights.addLightSource(this.lumPowderSource);
				this.enabled = true;
			}else if(this.enabled){
				DynamicLights.removeLightSource(this.lumPowderSource);
				this.enabled = false;
			}
		}
	}

}
