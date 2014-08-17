package me.superckl.betteroceans.common.parts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.entity.EntityModularBoat;
import me.superckl.betteroceans.common.reference.BoatParts;
import me.superckl.betteroceans.common.utility.ConstructorWrapper;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.StringHelper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Parent class for all BoatParts. This contains the id to {@link ConstructorWrapper} mappings as well as a helper deserialize method;
 *
 * NOTE: if your constructor takes a primitive argument, you must use the wrapped form or deserialization will fail.
 * See {@link ConstructorWrapper} for more information.
 */
public abstract class BoatPart {

	//Used for networking
	public static int nextID = 0;
	@Getter
	private static Map<Integer, ConstructorWrapper<? extends BoatPart>> parts = new HashMap<Integer, ConstructorWrapper<? extends BoatPart>>();

	public static <T extends BoatPart> int registerPart(final Class<T> partClass, final Object ... arguments){
		BoatPart.parts.put(BoatPart.nextID, new ConstructorWrapper<T>(partClass, arguments));
		LogHelper.debug(StringHelper.build("Registered boat part ", partClass.getCanonicalName(), " with ID ", BoatPart.nextID));
		return BoatPart.nextID++;
	}

	public static BoatPart deserialize(final int id){
		return BoatPart.parts.get(id).newInstance();
	}

	public static BoatPart getPartByTypeAndMaterial(final Type type, final Material material){
		return BoatPart.getWrapperFor(type, material).newInstance();
	}

	public static ConstructorWrapper<? extends BoatPart> getWrapperFor(final Type type, final Material material){
		for(final BoatPart part:BoatParts.allParts)
			if(part.getType() == type && part.getMaterial() == material)
				return BoatPart.parts.get(part.getPartConstructorID());
		return BoatPart.parts.get(0); //Temp while all materials not done
	}

	protected ResourceLocation texture;
	protected EntityBOBoat entity;

	public abstract Type getType();
	public abstract List<ItemStack> getCraftingIngredients();
	public abstract Material getMaterial();
	public abstract ItemStack getCraftingResult();
	public abstract int getMaxNumberOnBoat();
	public abstract int getPartConstructorID();
	public abstract boolean shouldDrop(final Random random);
	/**
	 * This method will not be called if {@link #useOverallComplexity() useOverallComplexity} returns true.
	 * The check that must be run iterates through all parts and requirements, twice. If this method is not necessary, don't use it;
	 * @param required The map to be filled with required {@link Type};
	 */
	public abstract void getRequiredTypesWithComplexities(final List<TypeRequirement> required);

	/**
	 * Used to determine if the boat will break upon impact. Wooden parts have a modifier of .85.
	 */
	public double getIntegrityFactor(){
		return this.getMaterial().getDefaultIntegrityFactor();
	}

	public int getComplexity(){
		return this.getMaterial().getDefaultComplexity();
	}

	/**
	 * Determines if this parts complexity should affect the overall complexity of the boat
	 */
	public boolean affectsOverallComplexity(){
		return true;
	}

	/**
	 * Used for display purposes
	 */
	public String getNiceName(){
		return this.getCraftingResult().getDisplayName();
	}

	/**
	 * If this returns true, {@link #getRequiredTypesWithComplexities(Map) getRequiredTypesWithComplexities} will not be called and {@link #getRequiredComplexity() getRequiredComplexity} will.
	 * The returned value will be compared to {@link EntityModularBoat#getOverallComplexity() getOverallComplexity}.
	 */
	public boolean useOverallComplexity(){
		return false;
	}

	/**
	 * This method will only be called if {@link #useOverallComplexity() useOverallComplexity} returns true.
	 */
	public int getRequiredComplexity(){
		return this.getComplexity();
	}

	public double getSpeedModifier(){
		return 1D;
	}
	public double getSinkChanceModifier(){
		return 1D;
	}
	public double getTurnModifier(){
		return 1D;
	}

	public ResourceLocation getTexture(){
		if(this.texture == null)
			this.texture = new ResourceLocation(this.getMaterial().getDefaultResourceLocation());
		return this.texture;
	}
	public EntityBOBoat getOnePartBoat(final World world){
		if(this.entity == null){
			this.entity = new EntityBOBoat(world);
			this.entity.addPart(this, false, true);
		}
		return this.entity;
	}

	/**
	 * This is called every time the entity is rendered. DO NOT MAKE A NEW LIST EVERY TIME! LAzily initialize it and store it.
	 * Make sure to add @SideOnly with Side.Client
	 */
	@SideOnly(Side.CLIENT)
	public abstract List<ModelRenderer> getRenderers(final ModelBase base);

	@RequiredArgsConstructor
	public static enum Type{
		BOTTOM(1),
		SIDE(2),
		END(4);

		@Getter
		private final int dataBit;

		public static Type getByData(final int data){
			for(final Type type:Type.values())
				if((type.getDataBit() & data) == type.getDataBit())
					return type;
			return null;
		}
	}

	@RequiredArgsConstructor
	public static enum Material{
		WOOD(32, "textures/entity/boat.png", new ItemStack(Blocks.planks), 1, .85D),
		IRON(64, "", new ItemStack(Items.iron_ingot), 2, 1.2D),//TODO
		GLASS(128, "", new ItemStack(Blocks.glass), 1, .6D);//TODO

		@Getter
		private final int dataBit;
		@Getter
		private final String defaultResourceLocation;
		@Getter
		private final ItemStack itemRepresentation;
		@Getter
		private final int defaultComplexity;
		@Getter
		private final double defaultIntegrityFactor;

		public static Material getByData(final int data){
			for(final Material mat:Material.values())
				if((mat.getDataBit() & data) == mat.getDataBit())
					return mat;
			return null;
		}

	}

}
