package me.superckl.betteroceans.common.nets;

import me.superckl.betteroceans.common.entity.EntityModularBoat;

public interface IItemNet {

	public boolean canAttachTo(final EntityModularBoat boat);
	public INet toNet(final int damage);

}
