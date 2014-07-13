package me.superckl.betteroceans.common.nets;

import me.superckl.betteroceans.common.entity.IEntityBoat;

public interface IItemNet {

	public boolean canAttachTo(final IEntityBoat boat);
	public INet toNet(final int damage);

}
