package me.superckl.betteroceans.common.parts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.superckl.betteroceans.common.parts.BoatPart.Type;

@AllArgsConstructor
public class TypeRequirement {

	@Getter
	@Setter
	private Type type;
	@Getter
	@Setter
	private int complexity;

}
