package me.superckl.betteroceans.client.render.entity;

import me.superckl.betteroceans.client.model.entity.ModelBOBoat;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.reference.RenderData;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.particle.EntitySplashFX;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBOBoat extends Render{

	/** instance of ModelBoat for rendering */
	protected ModelBase modelBoat;
	public RenderBOBoat()
	{
		this.shadowSize = 0.5F;
		this.modelBoat = new ModelBOBoat();
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(final EntityBOBoat boat, final double par2, final double par4, final double par6, final float par8, final float par9, final boolean doRotate)
	{
		if(boat.isSinking()){
			final EntitySplashFX leak = new EntitySplashFX(boat.worldObj, boat.posX, boat.posY+.02, boat.posZ, boat.motionX, boat.motionY+2, boat.motionZ);
			boat.worldObj.spawnEntityInWorld(leak);
		}
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		if(doRotate && boat.isRenderWithRotation())
			GL11.glRotatef(180.0F - boat.renderYawOffset++, 1F, 1F, 1F);
		else
			GL11.glRotatef(180.0F - par8, 0.0F, 1.0F, 0.0F);
		final float f2 = boat.getTimeSinceHit() - par9;
		float f3 = boat.getDamageTaken() - par9;

		if (f3 < 0.0F)
			f3 = 0.0F;

		if (f2 > 0.0F)
			GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0F * boat.getForwardDirection(), 1.0F, 0.0F, 0.0F);

		final float f4 = 0.75F;
		GL11.glScalef(f4, f4, f4);
		GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		this.modelBoat.render(boat, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9)
	{
		this.doRender((EntityBOBoat)par1Entity, par2, par4, par6, par8, par9, true);
	}

	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return RenderData.WOOD_BOAT;
	}

}
