package me.superckl.betteroceans.common.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.superckl.betteroceans.common.entity.EntityModularBoat;
import me.superckl.betteroceans.common.parts.BoatPart;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

@RequiredArgsConstructor
public abstract class ModularBoatEvent extends Event{

	@Getter
	private final EntityModularBoat boat;

	@Cancelable
	public static class PartAdd extends ModularBoatEvent{

		@Getter
		private final BoatPart part;

		public PartAdd(final EntityModularBoat boat, final BoatPart part) {
			super(boat);
			this.part = part;
		}

	}

}
