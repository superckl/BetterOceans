package me.superckl.betteroceans.common.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.superckl.betteroceans.common.gen.WorldGeneratorTrench;
import net.minecraft.world.chunk.Chunk;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

@RequiredArgsConstructor
public abstract class TrenchChunkEvent extends Event{

	@Getter
	private final Chunk chunk;
	@Getter
	private final WorldGeneratorTrench generator;

	@Cancelable
	public static class Enter extends TrenchChunkEvent{

		public Enter(final Chunk chunk, final WorldGeneratorTrench generator) {
			super(chunk, generator);
		}

	}

	@Cancelable
	public static class Leave extends TrenchChunkEvent{

		public Leave(final Chunk chunk, final WorldGeneratorTrench generator) {
			super(chunk, generator);
		}

	}

}
