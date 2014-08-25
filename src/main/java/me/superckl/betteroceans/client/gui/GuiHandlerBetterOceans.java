package me.superckl.betteroceans.client.gui;

import me.superckl.betteroceans.common.container.ContainerBoatbench;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import me.superckl.betteroceans.common.parts.BoatPart.Material;
import me.superckl.betteroceans.common.parts.BoatPart.Type;
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
		if(te != null && te instanceof TileEntityBoatbench)
			switch(ID){

			case ModData.GUIIDs.BASIC_BOAT_BENCH:
				return new ContainerBoatbench(player.inventory, (TileEntityBoatbench) te);
			case ModData.GUIIDs.INTER_BOAT_BENCH:
				return new ContainerBoatbench(player.inventory, (TileEntityBoatbench) te);
			}
		return null;
	}

	@Override
	public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world,
			final int x, final int y, final int z) {
		final TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityBoatbench)
			switch(ID){

			case ModData.GUIIDs.BASIC_BOAT_BENCH:
				return new GuiContainerBoatBench("basicboatbench", new ContainerBoatbench(player.inventory, (TileEntityBoatbench) te), (TileEntityBoatbench) te,
						new Type[] {Type.BOTTOM, Type.SIDE, Type.END}, new Material[] {Material.WOOD});
			case ModData.GUIIDs.INTER_BOAT_BENCH:
				return new GuiContainerBoatBench("interboatbench", new ContainerBoatbench(player.inventory, (TileEntityBoatbench) te), (TileEntityBoatbench) te,
						new Type[] {Type.BOTTOM, Type.SIDE, Type.END}, new Material[] {Material.WOOD, Material.IRON});
			}
		return null;
	}

}
