package me.superckl.betteroceans.common.entity;

import java.util.List;

import me.superckl.betteroceans.common.nets.INet;
import me.superckl.betteroceans.common.parts.BoatPart;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class EntityModularBoat extends Entity{

	public EntityModularBoat(final World world){
		super(world);
	}

	public abstract List<BoatPart> getBoatParts();

	public abstract INet getAttachedNet();

	public abstract void attachNet(final INet net);

	public abstract boolean hasNetAttatched();

	public abstract boolean isComplete();

	public abstract boolean addPart(final BoatPart part);

}
