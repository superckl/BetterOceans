package me.superckl.betteroceans.common.parts;

import java.util.List;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;
import me.superckl.betteroceans.common.BoatPart;

public abstract class PartSide extends BoatPart{

	protected List<ModelRenderer> renderers;
	protected final boolean leftSide;
	
	public PartSide(boolean leftSide){
		this.leftSide = leftSide;
	}
	
	@Override
	public Type getType() {
		return Type.SIDE;
	}

	@Override
	public List<ModelRenderer> getRenderers(ModelBase base) {
		// TODO Auto-generated method stub
		return null;
	}

}
