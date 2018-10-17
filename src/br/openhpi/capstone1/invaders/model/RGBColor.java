package br.openhpi.capstone1.invaders.model;


public class RGBColor {
	
	// this class is used to determine the color of objects (elements)
	
	// these are the color attributes
	public float red;
	public float green;
	public float blue;

	// can be constructed with custom values...
	public RGBColor(float red, float green, float blue) {
		this.red   = red;
		this.green = green;
		this.blue  = blue;
	}
	
	// ...or default values (white)
	public RGBColor() {
		this.red   = 255;
		this.green = 255;
		this.blue  = 255;
	}
}
