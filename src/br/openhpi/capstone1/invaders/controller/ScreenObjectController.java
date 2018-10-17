package br.openhpi.capstone1.invaders.controller;

import br.openhpi.capstone1.invaders.model.ScreenObject;

public class ScreenObjectController {
	
	// this is the base class for every screen object (element) controller
	// all controllers must have a screen object assigned to them...
	protected ScreenObject screenObject;

	public ScreenObjectController(ScreenObject screenObject) {
		// ...which is done here in the constructor...
		this.screenObject = screenObject;
	}
	
	// ... and must at least be able to call the object's "draw" method
	public void draw() {
		screenObject.draw();
	}
}
