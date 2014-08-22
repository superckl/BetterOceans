package me.superckl.betteroceans.client.handler;

import me.superckl.betteroceans.common.entity.StaminaExtendedProperties;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.utility.BlockHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RenderTickHandler{

	private final Minecraft mc;
	public static int lastDepth = 0;
	private final ResourceLocation gauges = new ResourceLocation(ModData.MOD_ID+":textures/gui/gauges.png");

	public RenderTickHandler(){
		this.mc = Minecraft.getMinecraft();
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(receiveCanceled = false)
	public void onRenderTick(final RenderGameOverlayEvent.Text e){
		//Depth Sounder
		if(this.mc.thePlayer != null && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() == ModItems.depthSounder){
			final int x = (int) Math.floor(this.mc.thePlayer.posX);
			int y = (int) Math.floor(this.mc.thePlayer.posY)-1;
			final int z = (int) Math.floor(this.mc.thePlayer.posZ);
			Fluid fluid = FluidRegistry.lookupFluidForBlock(this.mc.thePlayer.worldObj.getBlock(x, y, z));
			if(fluid == null && this.mc.thePlayer.ridingEntity != null){
				y = (int) Math.floor(this.mc.thePlayer.ridingEntity.posY);
				fluid = FluidRegistry.lookupFluidForBlock(this.mc.thePlayer.worldObj.getBlock(x, y, z));
				if(fluid == null){
					y--;
					fluid = FluidRegistry.lookupFluidForBlock(this.mc.thePlayer.worldObj.getBlock(x, y, z));
				}
			}
			if(fluid == null){
				e.left.add("Depth: Error");
				//mc.fontRenderer.drawStringWithShadow("Depth: Error", 5, 5, 0xFFFFFF);
				RenderTickHandler.lastDepth = 0;
				return;
			}
			final int depth = BlockHelper.getFluidDepth(this.mc.thePlayer.worldObj, x, y, z);
			RenderTickHandler.lastDepth = depth;
			e.left.add(String.format("Depth: %d - %s", depth, StringUtils.capitalize(fluid.getName().toLowerCase())));
			//mc.fontRenderer.drawStringWithShadow(String.format("Depth: %d - %s", depth, StringUtils.capitalize(fluid.getName().toLowerCase())), 5, 5, 0xFFFFFF);

		}

		IExtendedEntityProperties staminaProp;
		if(this.mc.thePlayer != null && this.mc.thePlayer.isInWater() && (staminaProp = this.mc.thePlayer.getExtendedProperties("swimStamina")) != null){
			final int index = (int) Math.ceil(((StaminaExtendedProperties)staminaProp).getStamina()*14F/100F);
			final ScaledResolution r = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
			final float scale = 2.3F;
			int x = (int) (r.getScaledWidth()-16*scale-10);
			int y = (int) (r.getScaledHeight()-16*scale-5);
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			this.mc.renderEngine.bindTexture(this.gauges);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			x /= scale;
			y /= scale;
			GL11.glScalef(scale, scale, scale);
			this.mc.ingameGUI.drawTexturedModalRect(x, y, index*15, 0, 15, 16);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}
}
