package me.superckl.betteroceans.network;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.utility.BoatHelper;
import me.superckl.betteroceans.common.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@RequiredArgsConstructor
public class MessagePartUpdate implements IMessage{

	@Getter
	@Setter
	@NonNull
	private EntityBOBoat boat;
	@SideOnly(Side.CLIENT)
	@Getter
	private List<BoatPart> parts;

	public MessagePartUpdate(){

	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		final Entity ent = Minecraft.getMinecraft().theWorld.getEntityByID(buf.readInt());
		LogHelper.info(ent);
		if(ent == null){
			LogHelper.error("Failed to deserialize EntityBOBoat!");
			return;
		}
		this.boat = (EntityBOBoat) ent;//NO WORKO
		this.parts = new ArrayList<BoatPart>();
		int amount = buf.readInt();
		while(amount > 0 && buf.readableBytes() > 0){
			this.parts.add(BoatHelper.readPartFromBuffer(buf));
			amount--;
		}
		//this.part = BoatPart.deserialize(buf);
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		if(this.boat == null)
			return;
		buf.writeInt(this.boat.getEntityId());
		buf.writeInt(this.boat.getBoatParts().size());
		for(final BoatPart part:this.boat.getBoatParts()){
			BoatHelper.writePartToBuffer(part, buf);
		}
		//this.part.serialize(buf);
	}

}
