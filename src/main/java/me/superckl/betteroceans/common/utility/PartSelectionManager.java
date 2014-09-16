package me.superckl.betteroceans.common.utility;

import java.util.Arrays;
import java.util.List;

import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.parts.BoatPart.Material;
import me.superckl.betteroceans.common.parts.PartBottom;
import me.superckl.betteroceans.common.parts.PartEnd;
import me.superckl.betteroceans.common.parts.PartSide;

public class PartSelectionManager implements Cloneable{

	public static final PartSelectionManager BASIC_BENCH = new PartSelectionManager();
	public static final PartSelectionManager INTER_BENCH = new PartSelectionManager();

	static{
		PartSelectionManager.BASIC_BENCH.add(Material.WOOD, Arrays.asList(new PartBottom.PartWoodenBottom(), new PartSide.PartWoodenSide(true), new PartEnd.PartWoodenEnd(true)));
		PartSelectionManager.INTER_BENCH.add(Material.WOOD, Arrays.asList(new PartBottom.PartWoodenBottom(), new PartSide.PartWoodenSide(true), new PartEnd.PartWoodenEnd(true)));
		PartSelectionManager.INTER_BENCH.add(Material.IRON, Arrays.asList(new PartBottom.PartIronBottom(), new PartSide.PartIronSide(true), new PartEnd.PartIronEnd(true)));
	}

	private Material[] materials = new Material[0];
	private int matIndex;
	private List<BoatPart>[] parts = new List[0];
	private int partIndex;

	public PartSelectionManager add(final Material material, final List<BoatPart> parts){
		if(material == null || parts == null)
			return this;
		if(parts.size() < 1)
			return this;
		int index;
		if((index = CollectionHelper.find(material, this.materials)) != -1){
			final List<BoatPart> partList = this.parts[index];
			partList.addAll(parts);
		}else{
			index = this.materials.length;
			this.materials = Arrays.copyOf(this.materials, index+1);
			this.parts = Arrays.copyOf(this.parts, index+1);
			this.materials[index] = material;
			this.parts[index] = parts;
		}
		return this;
	}

	public BoatPart getCurrentPart(){
		return this.parts[this.matIndex].get(this.partIndex);
	}

	public Material getCurrentMaterial(){
		return this.materials[this.matIndex];
	}

	public Material nextMaterial(){
		if(this.matIndex >= this.materials.length - 1)
			this.matIndex = -1;
		final Material mat = this.materials[++this.matIndex];
		this.onMaterialChangeCheck();
		return mat;
	}

	public Material previousMaterial(){
		if(this.matIndex <= 0)
			this.matIndex = this.materials.length;
		final Material mat = this.materials[--this.matIndex];
		this.onMaterialChangeCheck();
		return mat;
	}

	private void onMaterialChangeCheck(){
		if(this.partIndex >= this.parts[this.matIndex].size())
			this.partIndex = this.parts[this.matIndex].size()-1;
	}

	public BoatPart nextPart(){
		if(this.partIndex >= this.parts[this.matIndex].size()-1)
			this.partIndex = -1;
		return this.parts[this.matIndex].get(++this.partIndex);
	}

	public BoatPart previousPart(){
		if(this.partIndex <= 0)
			this.partIndex = this.parts[this.matIndex].size();
		return this.parts[this.matIndex].get(--this.partIndex);
	}

	public boolean advanceTo(final BoatPart part){
		for(int i = 0; i < this.parts.length; i++){
			final List<BoatPart> parts = this.parts[i];
			for(int j = 0; j < parts.size(); j++)
				if(parts.get(j).equals(part)){
					this.matIndex = i;
					this.partIndex = j;
					return true;
				}
		}
		return false;
	}

	@Override
	public PartSelectionManager clone(){
		final PartSelectionManager clone = new PartSelectionManager();
		clone.materials = this.materials;
		clone.matIndex = this.matIndex;
		clone.parts = this.parts;
		clone.partIndex = this.partIndex;
		return clone;
	}

}
