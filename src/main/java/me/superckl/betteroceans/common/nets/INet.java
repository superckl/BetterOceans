package me.superckl.betteroceans.common.nets;

import java.util.Set;

import me.superckl.betteroceans.common.entity.IEntityBoat;
import net.minecraft.item.ItemStack;

public interface INet {

	public NetState getNetState();
	public int getDamage();
	public int getFishAmount();
	public Set<ItemStack> getFish(final boolean clear);
	public boolean isAttatched();
	public IEntityBoat getAttatchedTo();
	public void preAttatchedUpdate();
	public void postAttahcedUpdate();
	public ItemStack toItemStack();

}
