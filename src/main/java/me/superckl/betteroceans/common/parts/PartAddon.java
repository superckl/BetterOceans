package me.superckl.betteroceans.common.parts;


public abstract class PartAddon extends BoatPart{

	@Override
	public Type getType() {
		return Type.ADDON;
	}

	@Override
	public Material getMaterial() {
		return Material.UNSUPPORTED;
	}

	@Override
	public boolean affectsOverallComplexity(){
		return false;
	}

}
