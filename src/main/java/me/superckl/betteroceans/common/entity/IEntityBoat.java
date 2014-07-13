package me.superckl.betteroceans.common.entity;

import me.superckl.betteroceans.common.nets.INet;

public interface IEntityBoat {

	public void attachNet(final INet net);
	public boolean hasNetAttatched();
	public INet getAttatchedNet();

}
