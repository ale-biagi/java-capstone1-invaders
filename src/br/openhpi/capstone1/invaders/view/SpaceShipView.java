package br.openhpi.capstone1.invaders.view;

import br.openhpi.capstone1.invaders.model.GameObject;
import br.openhpi.capstone1.invaders.model.Screen;
import processing.core.PApplet;
import processing.core.PImage;

public class SpaceShipView extends Observer {

	// the space ship is stored in a PNG image
	private PImage ship;

	// this observer observes the state and behavior of a space ship to update the screen
	// so it needs to have a space ship object assigned...

	public SpaceShipView(PApplet display, GameObject object) {
		super(display, object);
		this.ship = display.loadImage("spaceship-01.png", "png");
	}

	@Override
	public void update() {
		// with no more balls the game is over
		if (this.object.scorer.ships < 0) {
			display.fill(255,0,0);
			display.stroke(255);
			display.rect((Screen.WIDTH - 450)/2,
						this.object.scorer.size.height + ((Screen.HEIGHT - this.object.scorer.size.height)/2) - 50,
						450,
						100);
			display.textSize(48);
			display.fill(255);
			display.text("GAME OVER",((Screen.WIDTH - 450)/2) + 90, this.object.scorer.size.height + ((Screen.HEIGHT - this.object.scorer.size.height)/2) + 18);
			display.redraw();
            return;
		}
		
		if (!this.object.isExploding) {
			this.ship = display.loadImage("spaceship-01.png", "png");
			display.image(this.ship, this.object.screenPosition.x, this.object.screenPosition.y);
		} else {
			String frameImage = "frame_" + ((this.object.explosionFrame < 10) ? "0" : "") + this.object.explosionFrame + "_delay-0.03s.png";			
			this.ship = display.loadImage(frameImage, "png");
			display.image(this.ship, this.object.screenPosition.x, this.object.screenPosition.y);
		}
		display.redraw();
	}

}
