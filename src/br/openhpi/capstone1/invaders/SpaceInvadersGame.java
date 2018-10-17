package br.openhpi.capstone1.invaders;

import br.openhpi.capstone1.invaders.controller.*;
import br.openhpi.capstone1.invaders.model.*;
import processing.core.PApplet;
import processing.event.KeyEvent;

public class SpaceInvadersGame extends PApplet {

	// this is the main game controller that holds all other controllers
	private GameController gameController;
	
	@Override
	public void settings() {
		// setup screen size and frame rate
		// OBS: modify the values of the constants WIDTH and HEIGHT in the Screen class to change screen size
		size(Screen.WIDTH, Screen.HEIGHT);
	}	

	@Override
	public void setup() {  
		frameRate(30);
		gameController = new GameController(this);
	}
	
	@Override
	public void draw() {
		// infinite loop that keeps refreshing the background and moving game elements
		gameController.handleGame();
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		// handle key event
		gameController.handleKeyEvent(event);
	}
	
}
