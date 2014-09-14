package me.superckl.betteroceans.client.render.block;

import me.superckl.betteroceans.client.model.block.ModelLantern;
import me.superckl.betteroceans.common.entity.tile.TileEntityLantern;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.reference.RenderData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderLantern extends TileEntitySpecialRenderer implements IItemRenderer{

	private final ModelLantern model = new ModelLantern();
	private final Minecraft mc = Minecraft.getMinecraft();
	private final RenderItem renderItem = new RenderItem(){

		@Override
		public boolean shouldBob() {
			return false;
		}

	};
	private final ItemStack[] powders;

	public RenderLantern() {
		this.renderItem.setRenderManager(RenderManager.instance);
		this.powders = new ItemStack[5];
		for(int i = 0; i < 5; i++)
			this.powders[i] = new ItemStack(ModItems.lumPowder, 1, i);
	}

	@Override
	public void renderTileEntityAt(final TileEntity te, final double x,
			final double y, final double z, final float f) {
		if(te instanceof TileEntityLantern == false)
			return;
		this.renderLantern((float) x, (float) y, (float) z);
		this.renderPowder((float) x, (float) y, (float) z,te.getWorldObj(), ((TileEntityLantern)te).getLumPowder());

	}

	public void renderLantern(final float x, final float y, final float z){
		GL11.glPushMatrix();
		GL11.glTranslatef(x + 0.5F, y + 1.5F, z + 0.5F);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		this.mc.renderEngine.bindTexture(RenderData.LANTERN);
		this.model.renderAll();
		GL11.glPopMatrix();
	}

	public void renderPowder(final float x, final float y, final float z, final World world, final ItemStack powder){
		GL11.glPushMatrix();
		final EntityItem entityItem = new EntityItem(world);
		entityItem.hoverStart = 0.0F;
		entityItem.setEntityItemStack(powder);

		GL11.glTranslatef(x + 0.5F, y+.045F, z + 0.5F);
		GL11.glScalef(0.65F, 0.65F, 0.65F);

		GL11.glRotatef(45F, 0F, 1F, 0F);
		this.renderItem.doRender(entityItem, 0, 0, 0, 0, 0);

		GL11.glRotatef(90F, 0F, 1F, 0F);
		this.renderItem.doRender(entityItem, 0, 0, 0, 0, 0);

		GL11.glPopMatrix();
	}

	@Override
	public boolean handleRenderType(final ItemStack item, final ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(final ItemRenderType type, final ItemStack item,
			final ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(final ItemRenderType type, ItemStack item, final Object... data) {
		item = new ItemStack(ModItems.lumPowder, 1, item.getItemDamage());
		switch(type){
		case ENTITY:
		{
			GL11.glPushMatrix();
			GL11.glScalef(2F, 2F, 2F);
			this.renderLantern(-0.5F, 0.0F, -0.5F);
			this.renderPowder(-0.5F, 0.0F, -0.5F, ((Entity)data[1]).worldObj, item);
			GL11.glPopMatrix();
			break;
		}
		case EQUIPPED:
		{
			this.renderLantern(0.0F, 0.0F, 0.0F);
			this.renderPowder(0.0F, 0.0F, 0.0F, ((Entity)data[1]).worldObj, item);
			break;
		}
		case EQUIPPED_FIRST_PERSON:
		{
			this.renderLantern(0.0F, 0.0F, 0.0F);
			this.renderPowder(0.0F, 0.0F, 0.0F, ((Entity)data[1]).worldObj, item);
			break;
		}
		case INVENTORY:
		{
			this.renderLantern(0.0F, 0.0F, 0.0F);
			//renderPowder(-1.0F, -0.9F, 0.0F, this.mc.theWorld, item);
			break;
		}
		default:
			break;
		}
	}

}
