package br.openhpi.capstone1.invaders.model;

import java.util.ArrayList;
import java.util.List;

import br.openhpi.capstone1.invaders.view.Observer;
import processing.core.PImage;

public class SpaceObject extends GameObject {

	public PImage img;

	public List<Observer> observers = new ArrayList<Observer>();

	public SpaceObject(ScoreBoard scorer, Background background, ScreenPosition screenPosition, Size size, ObjectType type) {
		super(scorer, background, screenPosition, size);
		this.type = type;
		this.keepMoving = true;
		this.direction = (this.type == ObjectType.FIGHTER_SHOT) ? Direction.UP : Direction.DOWN; 
		this.img = null;
	}

	@Override
	protected void bounce() {
		// space objects do not bounce
	}

	@Override
	protected void spawn() {
		// only enemies are spawn
		if (this.type == ObjectType.ENEMY) {
			background.spawnEnemies(1);
			notifyAllObservers();
		}
	}
}
