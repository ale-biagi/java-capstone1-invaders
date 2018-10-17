package br.openhpi.capstone1.invaders.model;


public class ScreenPosition {

	// this class is used to determine the position of game objects (elements) at the screen

	// these are the coordinate attributes (for the top left corner of the object)
	public int x;
	public int y;
	
	// can be constructed with custom coordinates...
	public ScreenPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// ...or default coordinates
	public ScreenPosition() {
		this(0, 0);
	}
}
