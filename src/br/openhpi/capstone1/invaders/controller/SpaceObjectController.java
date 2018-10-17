package br.openhpi.capstone1.invaders.controller;

import br.openhpi.capstone1.invaders.model.GameObject;
import br.openhpi.capstone1.invaders.model.ObjectType;
import br.openhpi.capstone1.invaders.model.Screen;

public class SpaceObjectController extends GameObjectController {

	public SpaceObjectController(GameObject object) {
		super(object);
	}
	
	// this return the controller's corresponding object
	public GameObject getObject() {
		return(this.object);
	}
	
	// this checks whether the object is off-screen
	public boolean isOffScreen() {
		return ((this.object.screenPosition.x + this.object.size.width) < 0  || 
				(this.object.screenPosition.y + this.object.size.height) < 0 ||
				this.object.screenPosition.x > Screen.WIDTH || 
				this.object.screenPosition.y > Screen.HEIGHT);
	}
	
	// this checks whether the object is an enemy
	public boolean isEnemy() {
		return(this.object.type == ObjectType.ENEMY);
	}

	// this checks whether the object is a shot
	public boolean isShot() {
		return(this.object.type == ObjectType.FIGHTER_SHOT || this.object.type == ObjectType.ENEMY_SHOT);
	}
}
