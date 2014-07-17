package me.superckl.betteroceans.client.gui;

import me.superckl.betteroceans.common.container.ContainerBasicBoatWorkbench;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatWorkbench;
import me.superckl.betteroceans.common.reference.ModData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandlerBetterOceans implements IGuiHandler{

	@Override
	public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world,
			final int x, final int y, final int z) {
		final TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityBoatWorkbench && ID == ModData.GUIIDs.BASIC_BOAT_BENCH)
			return new ContainerBasicBoatWorkbench(player.inventory, (TileEntityBoatWorkbench) te);
		return null;
	}

	@Override
	public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world,
			final int x, final int y, final int z) {
		final TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityBoatWorkbench && ID == ModData.GUIIDs.BASIC_BOAT_BENCH)
			return new GuiContainerBasicBoatWorkbench(player.inventory, (TileEntityBoatWorkbench) te);
		return null;
	}

}
