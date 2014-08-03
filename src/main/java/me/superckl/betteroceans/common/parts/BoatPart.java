package me.superckl.betteroceans.common.parts;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.utility.ConstructorWrapper;
import me.superckl.betteroceans.common.utility.LogHelper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

	public static <T extends BoatPart> int registerPart(final Class<T> partClass, Object ... arguments){
		BoatPart.parts.put(BoatPart.nextID, new ConstructorWrapper<T>(partClass, arguments));
		LogHelper.info(partClass.getCanonicalName()+" has ID "+BoatPart.nextID);
		return BoatPart.nextID++;
	}

	public static BoatPart deserialize(int id){
		/*try {
			final Class<? extends BoatPart> clazz = BoatPart.parts.get(comp.getInteger("ID"));
			if(clazz == null)
				return null;
			Constructor<? extends BoatPart> construct = null;
			if(!comp.hasKey("boolFlag")){
				LogHelper.info("no");
				construct = clazz.getDeclaredConstructor();
				return construct.newInstance();
			}else{//Let's try with boolean then
				LogHelper.info("yes");
				construct = clazz.getConstructor(boolean.class);
				return construct.newInstance(comp.getBoolean("boolFlag"));
			}
		} catch (final Exception e) {
			LogHelper.error("Failed to deserialize boat part!");
			e.printStackTrace();
		}*/
		return parts.get(id).newInstance();
	}

	protected ResourceLocation texture;
	protected EntityBOBoat entity;

	public abstract Type getType();
	public abstract List<ItemStack> getCraftingIngredients();
	public abstract Material getMaterial();
	public double getSpeedModifier(){
		return 1D;
	}
	public double getSinkChanceModifier(){
		return 1D;
	}
	public abstract ItemStack getCraftingResult();
	public ResourceLocation getTexture(){
		if(this.texture == null)
			this.texture = new ResourceLocation(this.getMaterial().getDefaultResourceLocation());
		return this.texture;
	}
	public EntityBOBoat getOnePartBoat(final World world){
		if(this.entity == null){
			this.entity = new EntityBOBoat(world);
			this.entity.addPart(this);
		}
		return this.entity;
	}

	public abstract int getMaxNumberOnBoat();

	/**
	 * This is called every time the entity is rendered. DO NOT MAKE A NEW LIST EVERY TIME! LAzily initialize it and store it.
	 * Make sure to add @SideOnly with Side.Client
	 */
	@SideOnly(Side.CLIENT)
	public abstract List<ModelRenderer> getRenderers(final ModelBase base);
	
	public abstract int getPartConstructorID();
	public static enum Type{
		BOTTOM,
		SIDE,
		END;
	}

	@RequiredArgsConstructor
	public static enum Material{
		WOOD("textures/entity/boat.png"),
		IRON(""),//TODO
		GLASS("");//TODO

		@Getter
		private final String defaultResourceLocation;

	}

}
