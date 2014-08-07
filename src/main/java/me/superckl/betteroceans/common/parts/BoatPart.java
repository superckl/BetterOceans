package me.superckl.betteroceans.common.parts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
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
		for(final BoatPart part:BoatParts.allParts)
			if(part.getType() == type && part.getMaterial() == material)
				return BoatPart.parts.get(part.getPartConstructorID()).newInstance();
		return new PartBottom.PartWoodenBottom(); //Temp while all materials not done
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
	 * Used to determine if the boat will break upon impact. wooden parts have a modifier of .85.
	 * @return
	 */
	public double getIntegrityFactor(){
		return this.getMaterial().getDefaultIntegrityFactor();
	}

	public int getComplexity(){
		return this.getMaterial().getDefaultComplexity();
	}
	public double getSpeedModifier(){
		return 1D;
	}
	public double getSinkChanceModifier(){
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
			this.entity.getBoatParts().add(this);
		}
		return this.entity;
	}

	/**
	 * This is called every time the entity is rendered. DO NOT MAKE A NEW LIST EVERY TIME! LAzily initialize it and store it.
	 * Make sure to add @SideOnly with Side.Client
	 */
	@SideOnly(Side.CLIENT)
	public abstract List<ModelRenderer> getRenderers(final ModelBase base);

	public static enum Type{
		BOTTOM,
		SIDE,
		END;
	}

	@RequiredArgsConstructor
	public static enum Material{
		WOOD("textures/entity/boat.png", new ItemStack(Blocks.planks), 0, .85D),
		IRON("", new ItemStack(Items.iron_ingot), 1, 1.2D),//TODO
		GLASS("", new ItemStack(Blocks.glass), 0, .6D);//TODO

		@Getter
		private final String defaultResourceLocation;
		@Getter
		private final ItemStack itemRepresentation;
		@Getter
		private final int defaultComplexity;
		@Getter
		private final double defaultIntegrityFactor;

	}

}
