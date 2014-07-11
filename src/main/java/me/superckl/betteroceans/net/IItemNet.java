package me.superckl.betteroceans.net;

import me.superckl.betteroceans.entity.IEntityBoat;

public interface IItemNet {

	public boolean canAttachTo(final IEntityBoat boat);
	public INet toNet(final int damage);

}
