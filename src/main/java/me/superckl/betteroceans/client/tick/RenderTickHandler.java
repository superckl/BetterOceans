package me.superckl.betteroceans.client.tick;

import org.apache.commons.lang3.StringUtils;

import me.superckl.betteroceans.reference.ModItems;
import me.superckl.betteroceans.utility.BlockHelper;
import me.superckl.betteroceans.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.item.EntityBoat;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

public class RenderTickHandler{

	private final Minecraft mc;
	
	public RenderTickHandler(){
		this.mc = Minecraft.getMinecraft();
	}
	
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent e){
		if(e.side != Side.CLIENT || e.type != TickEvent.Type.RENDER)
			return; //Just to be safe

		if(mc.thePlayer != null && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() == ModItems.depthSounder){
			ScaledResolution res = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			int x = (int) Math.floor(mc.thePlayer.posX); int y = (int) Math.floor(mc.thePlayer.posY)-1; int z = (int) Math.floor(mc.thePlayer.posZ);
			Fluid fluid = FluidRegistry.lookupFluidForBlock(mc.thePlayer.worldObj.getBlock(x, y, z));
			if(fluid == null && mc.thePlayer.ridingEntity != null){
				y = (int) Math.floor(mc.thePlayer.ridingEntity.posY);
				fluid = FluidRegistry.lookupFluidForBlock(mc.thePlayer.worldObj.getBlock(x, y, z));
				if(fluid == null){
					y--;
					fluid = FluidRegistry.lookupFluidForBlock(mc.thePlayer.worldObj.getBlock(x, y, z));
				}
			}
			if(fluid == null){
				mc.fontRenderer.drawStringWithShadow("Depth: Error", 5, 5, 0xFFFFFF);
				return;
			}
			int depth = BlockHelper.getFluidDepth(mc.thePlayer.worldObj, x, y, z);

			mc.fontRenderer.drawStringWithShadow(String.format("Depth: %d - %s", depth, StringUtils.capitalize(fluid.getName().toLowerCase())), 5, 5, 0xFFFFFF);
		}
	}
	
}
