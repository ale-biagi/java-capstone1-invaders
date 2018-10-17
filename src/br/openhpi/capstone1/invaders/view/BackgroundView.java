package br.openhpi.capstone1.invaders.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.openhpi.capstone1.invaders.model.Screen;
import br.openhpi.capstone1.invaders.model.ScreenObject;
import br.openhpi.capstone1.invaders.controller.SpaceObjectController;
import br.openhpi.capstone1.invaders.model.Background;
import processing.core.PApplet;
import processing.core.PGraphics;

public class BackgroundView extends Observer {

	// the background has the "shape" of space
	private PGraphics space;
	
	// it can move to provide the feeling the ship is moving forward
	private int firstImage_y;
	private int secondImage_y;

	// shots controller
	private int shootCount;

	// this observer observes the state and behavior of the screen background to
	// update the screen
	// so it needs to have a background object assigned...

	public BackgroundView(PApplet display, ScreenObject screen) {
		// ...which is done by its supper class constructor
		super(display, screen);

		firstImage_y = (Screen.HEIGHT * -1) + this.screen.screenPosition.y + 100;
		secondImage_y = this.screen.screenPosition.y;

		shootCount = 0;

		createSpaceBG();
	}

	private void createSpaceBG() {
		Random rnd = new Random();

		space = display.createGraphics(this.screen.size.width, this.screen.size.height);
		space.beginDraw();
		space.fill(this.screen.color.red, this.screen.color.green, this.screen.color.blue);
		space.rect(0, 0, this.screen.size.width, this.screen.size.height);
		space.noStroke();

		int xIncrement = rnd.nextInt(17) + 11;
		int yIncrement = rnd.nextInt(17) + 11;

		int yStart = rnd.nextInt(16) + 10;

		for (int y = yStart; y < this.screen.size.height; y += xIncrement) {
			int xStart = rnd.nextInt(21) + 16;
			for (int x = xStart; x < this.screen.size.width; x += yIncrement) {
				int color = (rnd.nextInt(1000) + 1 < 500) ? 0 : 255;
				int whichSize = rnd.nextInt(900) + 1;
				int size = (whichSize < 300) ? 1 : (whichSize + 1 < 600) ? 2 : 3;

				space.fill(color);
				space.rect(x, y, size, size);

				xIncrement = rnd.nextInt(17) + 11;
			}
			yIncrement = rnd.nextInt(17) + 11;
		}
		space.endDraw();
	}

	private void updateSpaceObjects() {
		// removal buffer
		List<SpaceObjectController> objectsToRemove = new ArrayList<SpaceObjectController>();

		// shots draw
		for (SpaceObjectController object : ((Background) this.screen).getObjects()) {
			object.move();
			if (object.isOffScreen() || object.hasExploded())
				objectsToRemove.add(object);
		}

		// off-screen and exploded objects removal
		int enemiesCount = 0;
		for (SpaceObjectController object : objectsToRemove) {
			enemiesCount += (object.isEnemy()) ? 1 : 0;
			((Background) this.screen).detachObject(object);
		}

		// spawn new enemies if necessary
		for (int i = 0; i < enemiesCount; i++) {
			((Background) this.screen).spawnEnemies(1);
		}

		// let enemies shoot
		if (shootCount % 10 == 0)
			((Background) this.screen).enemiesShoot();
		shootCount++;
		if (shootCount > 100000) shootCount = 0;
	}

	@Override
	public void update() {
		display.image(space, this.screen.screenPosition.x, firstImage_y);
		display.image(space, this.screen.screenPosition.x, secondImage_y);
		updateSpaceObjects();
		if (((Background) this.screen).keepMoving) {
			if (firstImage_y >= this.screen.screenPosition.y) {
				firstImage_y = (Screen.HEIGHT * -1) + this.screen.screenPosition.y + 100;
				secondImage_y = this.screen.screenPosition.y;
			} else {
				firstImage_y += 5;
				secondImage_y += 5;
			}
		}
		display.redraw();
	}
}
