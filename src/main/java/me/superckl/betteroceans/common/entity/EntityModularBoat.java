package me.superckl.betteroceans.common.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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

	public int getOverallComplexity(){
		final List<BoatPart> parts = new ArrayList<BoatPart>(this.getBoatParts());
		final Iterator<BoatPart> it = parts.iterator();
		while(it.hasNext())
			if(!it.next().affectsOverallComplexity())
				it.remove();
		final int[] complex = new int[parts.size()];
		for(int i = 0; i < complex.length; i++)
			complex[i] = parts.get(i).getComplexity();
		Arrays.sort(complex);
		return complex[0];
	}

}
