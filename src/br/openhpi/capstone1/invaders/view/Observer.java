package br.openhpi.capstone1.invaders.view;

import br.openhpi.capstone1.invaders.model.GameObject;
import br.openhpi.capstone1.invaders.model.ScreenObject;
import processing.core.PApplet;

public abstract class Observer {
	
	// this is the base abstract class for all "observers" (views)
	
	// every view must update elements in the screen
	// so it needs to have it (the "screen" hereby called "display") assigned...
	public PApplet display;	
	protected GameObject object;
	protected ScreenObject screen;

	public Observer(PApplet display, GameObject object) {
		// ...therefore it's done here in the constructor...
		this.display = display;
		
		// ...as well have it attached to the object that's going to be "observed"
		object.attach(this);
		this.object = object;
		this.screen = null;
	}

	public Observer(PApplet display, ScreenObject screen) {
		// ...therefore it's done here in the constructor...
		this.display = display;
		
		// ...as well have it attached to the object that's going to be "observed"
		screen.attach(this);
		this.object = null;
		this.screen = screen;
	}

	// finally and observer must "update" the screen in its own way
	public abstract void update();
}
