package br.openhpi.capstone1.invaders.model;


public class Size {
	
	// this class is used to determine the size of game objects (elements)
	
	// these are the dimension attributes
	public int width;
	public int height;
	
	// can be constructed with custom dimensions...
	public Size(int width, int height) {
		this.width = width;
		this.height = height;
	}

	// ...or default dimensions (this default is used by a paddle)
	public Size() {
		this(100, 100);
	}
}
