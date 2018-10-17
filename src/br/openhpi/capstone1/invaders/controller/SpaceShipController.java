package br.openhpi.capstone1.invaders.controller;

import br.openhpi.capstone1.invaders.model.Background;
import br.openhpi.capstone1.invaders.model.Direction;
import br.openhpi.capstone1.invaders.model.GameObject;
import processing.event.KeyEvent;

public class SpaceShipController extends GameObjectController {

	// this is the controller class for a space ship
	public SpaceShipController(GameObject gameObject, Background background) {
		super(gameObject);
	}

	// ...and additionally handle the key pressing event
	public void handleKeyEvent(KeyEvent event) {
		// get the code for the pressed key
		int keyCode = event.getKeyCode();
		if (keyCode != 17 && keyCode != 32 && keyCode != 37 && keyCode != 38 && keyCode != 39 && keyCode != 40)
			return;

		if (keyCode == 32) {
			// stop moving the object when the "space" key is hit
			object.keepMoving = false;
		} else {
			if (keyCode == 17) {
				shoot();
			} else {
				// change the object direction when the direction "arrow" keys are hit
				switch (keyCode) {
				case 39:
					object.direction = Direction.RIGHT;
					break;
				case 38:
					object.direction = Direction.UP;
					break;
				case 40:
					object.direction = Direction.DOWN;
					break;
				case 37:
					object.direction = Direction.LEFT;
					break;
				}
				// start moving the object
				object.keepMoving = true;
			}
		}
	}
}
