package me.superckl.betteroceans.entity;

import me.superckl.betteroceans.net.INet;

public interface IEntityBoat {

	public void attachNet(final INet net);
	public boolean hasNetAttatched();
	public INet getAttatchedNet();

}
