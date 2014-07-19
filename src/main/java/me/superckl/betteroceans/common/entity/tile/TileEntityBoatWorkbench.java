package me.superckl.betteroceans.common.entity.tile;

import java.util.Arrays;

import lombok.Getter;
import me.superckl.betteroceans.common.entity.IEntityBoat;
import me.superckl.betteroceans.common.entity.Rotatable;
import me.superckl.betteroceans.common.utility.RecipeHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class TileEntityBoatWorkbench extends TileEntity implements IInventory{

	@Getter
	private final ItemStack[] inventory = new ItemStack[10];
	@Getter
	private IEntityBoat activeSelection;

	public void setActiveSelection(final IEntityBoat selection){
		this.activeSelection = selection;
		if(selection instanceof Rotatable)
			((Rotatable)selection).setRenderWithRotation(true);
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(final int slot) {
		return this.inventory[slot];
	}

	public void onCraftingSlotPick(){
		RecipeHelper.removeItems(this.activeSelection.getCraftingIngredients(), this.inventory, true);
		this.checkRecipeCompletion();
	}

	@Override
	public ItemStack decrStackSize(final int slot, final int amount) {
		if (this.inventory[slot] != null)
		{
			ItemStack itemstack;

			if (this.inventory[slot].stackSize <= amount)
			{
				itemstack = this.inventory[slot];
				this.inventory[slot] = null;
				this.markDirty();
				this.checkRecipeCompletion();
				return itemstack;
			}
			else
			{
				itemstack = this.inventory[slot].splitStack(amount);

				if (this.inventory[slot].stackSize == 0)
					this.inventory[slot] = null;

				this.markDirty();
				this.checkRecipeCompletion();
				return itemstack;
			}
		} else{
			this.checkRecipeCompletion();
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int slot) {
		if (this.inventory[slot] != null)
		{
			final ItemStack itemstack = this.inventory[slot];
			this.inventory[slot] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(final int slot, final ItemStack stack) {
		this.inventory[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();

		this.markDirty();
		if(this.activeSelection != null)
			this.checkRecipeCompletion();
	}

	private void checkRecipeCompletion(){
		if(this.activeSelection == null)
			return;
		if(RecipeHelper.areItemsPresent(this.activeSelection.getCraftingIngredients(), Arrays.copyOf(this.inventory, 9), true))
			this.inventory[9] = new ItemStack(this.activeSelection.getItem());
		else
			this.inventory[9] = null;
	}

	@Override
	public String getInventoryName() {
		return "Basic Workbench";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(final int slot, final ItemStack stack) {
		return slot < 9;
	}

	@Override
	public void readFromNBT(final NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		final NBTTagList tagList = tagCompound.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++) {
			final NBTTagCompound tag = tagList.getCompoundTagAt(i);
			final byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < this.inventory.length)
				this.inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
		}
	}

	@Override
	public void writeToNBT(final NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		final NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < this.inventory.length; i++) {
			final ItemStack stack = this.inventory[i];
			if (stack != null) {
				final NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);
	}

}
