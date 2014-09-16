package me.superckl.betteroceans.common.parts;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.entity.EntityModularBoat;
import me.superckl.betteroceans.common.reference.BoatParts;
import me.superckl.betteroceans.common.reference.RenderData;
import me.superckl.betteroceans.common.utility.BOReflectionHelper;
import me.superckl.betteroceans.common.utility.ConstructorWrapper;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.StringHelper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Parent class for all BoatParts. This contains the id to {@link ConstructorWrapper} mappings as well as a helper deserialize method;
 *
 * NOTE: if your constructor takes a primitive argument, you must use the wrapped form or deserialization will fail.
 * See {@link ConstructorWrapper} for more information.
 */
public abstract class BoatPart implements Cloneable{

	//Used for networking
	public static int nextID = 0;
	@Getter
	private static Map<Integer, ConstructorWrapper<? extends BoatPart>> parts = new TreeMap<Integer, ConstructorWrapper<? extends BoatPart>>();

	/**
	 * Registers a part with the {@link #parts parts} Map. This is required for serialization to work properly (and for you to properly implement {@link #getPartID() getPartID}.
	 * @param partClass The class of your part.
	 * @param arguments Any arguments the constructor for your part takes. This is used in deserialization.
	 * @return The ID of your part. This should be stored and returned by {@link #getPartID() getPartID}.
	 */
	public static <T extends BoatPart> int registerPart(final Class<T> partClass, final Object ... arguments){
		BoatPart.parts.put(BoatPart.nextID, new ConstructorWrapper<T>(partClass, arguments));
		LogHelper.debug(StringHelper.build("Registered boat part ", partClass.getCanonicalName(), " with ID ", BoatPart.nextID));
		return BoatPart.nextID++;
	}

	public static BoatPart deserialize(final int id){
		return BoatPart.parts.get(id).newInstance();
	}

	public static BoatPart getPartByTypeAndMaterial(final Type type, final Material material){
		if(type == Type.ADDON){
			final StackTraceElement ste = BOReflectionHelper.retrieveCallingStackTraceElement();
			LogHelper.error(StringHelper.build("Cannot retrieve part for Type ADDON! Report this to the owner of the calling class: ", ste.getClassName(), ":"
					, ste.getMethodName(), ":", ste.getLineNumber()));
			return null;
		}
		return BoatPart.getWrapperFor(type, material).newInstance();
	}

	public static ConstructorWrapper<? extends BoatPart> getWrapperFor(final Type type, final Material material){
		if(type == Type.ADDON){
			final StackTraceElement ste = BOReflectionHelper.retrieveCallingStackTraceElement();
			LogHelper.error(StringHelper.build("Cannot retrieve part for Type ADDON! Report this to the owner of the calling class: ", ste.getClassName(), ":"
					, ste.getMethodName(), ":", ste.getLineNumber()));
			return null;
		}
		for(final BoatPart part:BoatParts.allParts)
			if(part.getType() == type && part.getMaterial() == material)
				return BoatPart.parts.get(part.getPartID());
		return BoatPart.parts.get(0); //Temp while all materials not done
	}

	protected EntityBOBoat entity;

	public abstract Type getType();
	/**
	 * This is called to get the default ingredients.
	 * This can be overriden in {@link BoatbenchRecipeHandler#registerRecipeOverride(int, IBoatPartRecipeHandler) registerRecipeOverride} if you want to override recipes of parts that are not yours.
	 */
	public abstract List<ItemStack> getCraftingIngredients();
	public abstract Material getMaterial();
	public abstract ItemStack getCraftingResult();
	public abstract int getMaxNumberOnBoat();
	/**
	 * @return The int returned by {@link #registerPart(Class, Object...) registerPart}.
	 */
	public abstract int getPartID();
	public abstract boolean shouldDrop(final Random random);
	/**
	 * If the part does not appear in {@link BoatParts#allParts allParts} this can return null.
	 * @return The path to the texture for the item
	 */
	public abstract String getItemTexture();
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
	/**
	 * If you want to decrease the turning rate, DO NOT go very far below 1. .9 is probably the minimum I would recommend. Otherwise the boat just won't turn...
	 * A small decrease will have a very large affect!
	 * The compounded factor is used to divide .6 by, meaning if the factor is less than .6, the boat won't turn at all. I warned you.
	 * @return
	 */
	public double getTurnModifier(){
		return 1D;
	}

	public boolean allowsVanillaTurning(){
		return false;
	}

	public int getCreationTime(){
		return 0;
	}

	public ResourceLocation getTexture(){
		return this.getMaterial().getDefaultTextureLocation();
	}

	@Override
	public BoatPart clone(){
		return BoatPart.deserialize(this.getPartID());
	}

	@Override
	public boolean equals(final Object obj) {
		return obj != null && (obj == this || obj instanceof BoatPart && ((BoatPart)obj).getPartID() == this.getPartID());
	}

	/**
	 * This is called every time the entity is rendered. DO NOT MAKE A NEW LIST EVERY TIME! LAzily initialize it and store it.
	 * Make sure to add @SideOnly with Side.Client
	 */
	@SideOnly(Side.CLIENT)
	public abstract List<ModelRenderer> getRenderers(final ModelBase base);

	@RequiredArgsConstructor
	public static enum Type{
		BOTTOM(),
		SIDE(),
		END(),
		ADDON();

		/**
		 * Helper method to {@link EnumHelper#addEnum(Class, String, Object...) addEnum}
		 * @param name The name of the new Type
		 */
		public static void addType(final String name){
			EnumHelper.addEnum(Type.class, name);
		}
	}

	@RequiredArgsConstructor
	public static enum Material{
		WOOD(RenderData.WOOD_BOAT, new ItemStack(Blocks.planks), 1, .85D),
		IRON(RenderData.IRON_BOAT, new ItemStack(Items.iron_ingot), 2, 1.2D),//TODO
		GLASS(null, new ItemStack(Blocks.glass), 1, .6D),//TODO
		UNSUPPORTED(null, null, 0, 0D);

		@Getter
		private final ResourceLocation defaultTextureLocation;
		@Getter
		private final ItemStack itemRepresentation;
		@Getter
		private final int defaultComplexity;
		@Getter
		private final double defaultIntegrityFactor;

		/**
		 * Helper method to {@link EnumHelper#addEnum(Class, String, Object...) addEnum}
		 * @param name The name of the new Type
		 * @param defaultTextureLocation The path to the texture to use by default for modeling. This can be null if all parts with this material override {@link BoatPart#getTexture() getTexture}.
		 */
		public static void addMaterial(final String name, final String defaultTextureLocation, final ItemStack itemRepresentation, final int defaultComplexity, final double defaultIntegrityFactor){
			EnumHelper.addEnum(Material.class, name, defaultTextureLocation, itemRepresentation, defaultComplexity, defaultIntegrityFactor);
		}

		public static class MaterialComparator implements Comparator<Material>{

			@Override
			public int compare(final Material m1, final Material m2) {
				return m1 == m2 ? 0:m1.ordinal() > m2.ordinal() ? 1:-1;
			}

		}

	}

}
