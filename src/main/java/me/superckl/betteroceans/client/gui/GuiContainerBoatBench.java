package me.superckl.betteroceans.client.gui;

import me.superckl.betteroceans.common.container.ContainerBasicBoatbench;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.parts.BoatPart.Material;
import me.superckl.betteroceans.common.parts.BoatPart.Type;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.NetworkData;
import me.superckl.betteroceans.common.utility.CollectionHelper;
import me.superckl.betteroceans.common.utility.RenderHelper;
import me.superckl.betteroceans.network.MessageSelectBoatPart;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public abstract class GuiContainerBoatBench extends GuiContainer{

	protected ResourceLocation texture = new ResourceLocation(ModData.MOD_ID+":textures/gui/basicbench.png");

	protected int typeIndex = -1;
	protected Type[] validTypes;
	protected int materialIndex = -1;
	protected Material[] validMaterials;
	protected BoatPart activePart;
	protected ItemStack partStack;

	protected int partX;
	protected int materialX;

	public GuiContainerBoatBench(final Container container, final TileEntityBoatbench te, final Type[] validTypes, final Material[] validMaterials) {
		super(container);
		this.validTypes = validTypes;
		this.validMaterials = validMaterials;
		if(te.getActiveSelection() != null){
			this.activePart = te.getActiveSelection().getBoatParts().get(0);
			this.partStack = this.activePart.getCraftingResult();
			this.typeIndex = CollectionHelper.find(this.activePart.getType(), Type.values());
			this.materialIndex = CollectionHelper.find(this.activePart.getMaterial(), Material.values());
		}
	}

	public void updateActivePart(){
		final BoatPart part = BoatPart.getPartByTypeAndMaterial(this.validTypes[this.typeIndex], this.validMaterials[this.materialIndex]);
		if(part == null)
			return;
		this.activePart = part;
		this.partStack = part.getCraftingResult();
		final TileEntityBoatbench te = ((ContainerBasicBoatbench)this.inventorySlots).getTileEntity();
		te.setActiveSelection(part.getOnePartBoat(te.getWorldObj()));
		NetworkData.PART_SELECT_CHANNEL.sendToServer(new MessageSelectBoatPart(te, part));
	}

	protected void setTexture(final ResourceLocation texture){
		this.texture = texture;
	}

	protected void setTexture(final ResourceLocation texture, final int xSize, final int ySize){
		this.setTexture(texture);
		this.xSize = xSize;
		this.ySize = ySize;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float p_146976_1_,
			final int p_146976_2_, final int p_146976_3_) {
		this.mc.renderEngine.bindTexture(this.texture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		final int xStart = (this.width - this.xSize) / 2;
		final int yStart = (this.height - this.ySize) / 2;
		RenderHelper.drawTexturedRect(this.texture, xStart, yStart, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize, 1F);
	}

}
