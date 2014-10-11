package me.superckl.betteroceans.common.event;

import lombok.Getter;
import me.superckl.betteroceans.common.gen.WorldGeneratorTrench;
import net.minecraft.world.chunk.Chunk;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

public class TrenchChunkEvent extends Event{

	@Getter
	private final Chunk chunk;
	@Getter
	private final WorldGeneratorTrench generator;

	public TrenchChunkEvent(final Chunk chunk, final WorldGeneratorTrench generator) {
		this.chunk = chunk;
		this.generator = generator;
	}

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
