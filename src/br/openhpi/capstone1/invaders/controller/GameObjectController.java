package br.openhpi.capstone1.invaders.controller;

import br.openhpi.capstone1.invaders.model.GameObject;

public abstract class GameObjectController {
	
	// this is the base abstract class for every game object (element) controller
	// all controllers must have a game object assigned to them...
	protected GameObject object;

	public GameObjectController(GameObject object) {
		// ...which is done here in the constructor...
		this.object = object;
	}
	
	// this method disposes all allocated memory for garbage collection
	public void dispose() {
		object.detachAllObservers();
		object = null;
	}
	
	// ..., must at least be able to call the object's "move" method...
	public void move() {
		object.move();
	}
	
	// ...and the object's "shoot" method.
	public void shoot() {
		object.shoot();
	}
	
	// check whether the object has shot
	public boolean hasShot() {
		return object.hasShot;
	}
	
	// check whether the object has exploded
	public boolean hasExploded() {
		return object.exploded;
	}
}