package me.superckl.betteroceans.client.render;

import java.util.HashSet;
import java.util.Set;

import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.parts.BoatPart.Material;
import me.superckl.betteroceans.common.parts.BoatPart.Type;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.utility.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

//Not used
public class ItemRenderBoatPart implements IItemRenderer{

	private final Minecraft mc = Minecraft.getMinecraft();
	private final Set<EntityBOBoat> boats = new HashSet<EntityBOBoat>();

	@Override
	public boolean handleRenderType(final ItemStack item, final ItemRenderType type) {
		return type == ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(final ItemRenderType type, final ItemStack item,
			final ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(final ItemRenderType type, final ItemStack item, final Object... data) {
		if(item.getItem() != ModItems.boatPart)
			return;
		final BoatPart part = BoatPart.getPartByTypeAndMaterial(Type.getByData(item.getItemDamage()), Material.getByData(item.getItemDamage()));
		EntityBOBoat boat = null;
		for(final EntityBOBoat b:this.boats)
			if(b.getBoatParts().get(0) == part)
				boat = b;
		if(boat == null){
			boat = part.getOnePartBoat(this.mc.theWorld);
			boat.setRenderWithRotation(true);
			boat.renderYawOffset = 45;
			this.boats.add(boat);
		}
		RenderHelper.renderEntityToGUI(boat, 0, 0, 6F);
		boat.renderYawOffset--;
	}

}
