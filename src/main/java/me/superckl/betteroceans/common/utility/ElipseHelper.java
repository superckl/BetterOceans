package me.superckl.betteroceans.common.utility;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class ElipseHelper {

	@Getter
	private final float centerX, centerY, axis1, axis2;

	@Getter
	@Setter
	private boolean isSwapXY = false;

	public boolean isInside(float x, float y){
		if(this.isSwapXY){
			final float temp = x;
			x = y;
			y = temp;
		}
		x -= this.centerX;
		y -= this.centerY;
		return x*x/(this.axis1*this.axis1)+y*y/(this.axis2*this.axis2) <= 1;
	}

}
