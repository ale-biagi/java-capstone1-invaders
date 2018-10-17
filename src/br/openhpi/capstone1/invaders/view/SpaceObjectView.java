package br.openhpi.capstone1.invaders.view;

import br.openhpi.capstone1.invaders.model.SpaceObject;
import br.openhpi.capstone1.invaders.model.GameObject;
import br.openhpi.capstone1.invaders.model.ObjectType;
import processing.core.PApplet;
import processing.core.PImage;

public class SpaceObjectView extends Observer {

	private PImage img;

	public SpaceObjectView(PApplet display, SpaceObject object) {
		super(display, object);
		if (object.type == ObjectType.ENEMY)
			img = display.loadImage("spaceship-02.png", "png");
	}

	private void drawShot(GameObject shot) {
		int stroke = (shot.type == ObjectType.FIGHTER_SHOT) ? display.color(255, 153, 0) : display.color(255, 204, 51);
		int fill = (shot.type == ObjectType.FIGHTER_SHOT) ? display.color(255, 204, 51) : display.color(255, 153, 0);
		display.stroke(stroke);
		display.fill(fill);
		display.ellipse(shot.screenPosition.x, shot.screenPosition.y, shot.size.width, shot.size.height);
		display.redraw();
	}

	private void drawEnemy(GameObject enemy) {
		if (!enemy.isExploding) {
			display.image(this.img, this.object.screenPosition.x, this.object.screenPosition.y);
		} else {
			String frameImage = "frame_" + ((this.object.explosionFrame < 10) ? "0" : "") + this.object.explosionFrame
					+ "_delay-0.03s.png";
			this.img = display.loadImage(frameImage, "png");
			display.image(this.img, this.object.screenPosition.x, this.object.screenPosition.y);
		}
		display.redraw();
	}

	@Override
	public void update() {
		if (this.object.type == ObjectType.FIGHTER_SHOT || object.type == ObjectType.ENEMY_SHOT) {
			drawShot(this.object);
		} else {
			drawEnemy(this.object);
		}
	}

}
