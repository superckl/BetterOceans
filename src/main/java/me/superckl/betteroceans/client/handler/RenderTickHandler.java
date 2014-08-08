package me.superckl.betteroceans.client.handler;

import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.utility.BlockHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RenderTickHandler{

	private final Minecraft mc;
	public static int lastDepth = 0;
	private final ResourceLocation circleTexture = new ResourceLocation(ModData.MOD_ID+":textures/gui/emptycircle.png");
	private final ResourceLocation staminaTexture = new ResourceLocation(ModData.MOD_ID+":textures/gui/staminacircle.png");

	public RenderTickHandler(){
		this.mc = Minecraft.getMinecraft();
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
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
		//Stamina gauge, commented until I get the textures
		/*if(this.mc.thePlayer != null && this.mc.thePlayer.isInWater() && this.mc.thePlayer.getExtendedProperties("swimStamina") != null){
			final int dim = 16;//???
			final int radius = dim/2;
			int x = this.mc.displayWidth-radius-5;
			int y = this.mc.displayHeight-radius-5;
			RenderHelper.drawTexturedCircle(x, y, radius, 100, circleTexture);
			float perc = ((StaminaExtendedProperties)this.mc.thePlayer.getExtendedProperties("swimStamina")).getStamina()/100F;
			int minY = (int) ((y+radius)-(dim*perc));
			RenderHelper.drawTexturedCircle(x, y, radius, x+radius, x-radius, y+radius, minY, (int) (100-80*(1-perc)), staminaTexture);
		}*/
	}

}
