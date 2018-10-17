package br.openhpi.capstone1.invaders.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.openhpi.capstone1.invaders.controller.SpaceObjectController;
import br.openhpi.capstone1.invaders.view.SpaceObjectView;

public class Background extends ScreenObject {

	private SpaceShip ship;
	public boolean keepMoving;

	private List<SpaceObjectController> objects = new ArrayList<SpaceObjectController>();

	public Background() {
		super(new ScreenPosition(0, 51), new Size(Screen.WIDTH, Screen.HEIGHT - 50), new RGBColor(0, 0, 0));
		this.keepMoving = true;
	}

	public void setShip(SpaceShip ship) {
		this.ship = ship;
	}
	
	public void attachObject(SpaceObjectController object) {
		objects.add(object);
	}

	public void detachObject(SpaceObjectController object) {
		objects.remove(object);
		object.dispose();
		object = null;
	}
	
	public void detachAllObjects() {
		// remove and clean all attached observers to update their view state
		while (objects.size() > 0) {
			detachObject(objects.get(0));
		}
	}

	public List<SpaceObjectController> getObjects() {
		return objects;
	}

	private boolean isOverlapping(int x, int y) {
		boolean overlapping = false;
		for (SpaceObjectController object : objects) {
			int xPos = object.getObject().screenPosition.x;
			int yPos = object.getObject().screenPosition.y;
			int xLowerBound = xPos - 105;
			int xUpperBound = xPos + 105;
			int yLowerBound = yPos - 105;
			int yUpperBound = yPos + 105;
			overlapping = ((x >= xLowerBound && x <= xUpperBound) && (y >= yLowerBound && y <= yUpperBound));
			if (overlapping)
				break;
		}
		return (overlapping);
	}

	public void addShot(GameObject fromObject) {
		SpaceObject shot = new SpaceObject(fromObject.scorer, this, new ScreenPosition(), new Size(8, 28),
				(fromObject.type == ObjectType.FIGHTER) ? ObjectType.FIGHTER_SHOT : ObjectType.ENEMY_SHOT);
		shot.screenPosition.x = fromObject.screenPosition.x + ((fromObject.size.width / 2) - shot.size.width) + 8;
		shot.screenPosition.y = fromObject.screenPosition.y
				+ ((fromObject.type == ObjectType.FIGHTER) ? -(shot.size.height - 20) : (fromObject.size.height - 12));
		SpaceObjectController shotController = new SpaceObjectController(shot);
		new SpaceObjectView(this.observers.get(0).display, shot).update();
		this.attachObject(shotController);
	}

	public void spawnEnemies(int numEnemies) {
		Random rnd = new Random();

		int x;
		int y;

		for (int i = 0; i < numEnemies; i++) {
			// avoid overlapping of enemies
			do {
				x = rnd.nextInt(Screen.WIDTH - 99);
				y = (numEnemies > 1) ? rnd.nextInt(Screen.HEIGHT - 400) : -50;
			} while (isOverlapping(x, y));

			SpaceObject enemy = new SpaceObject(this.ship.scorer, this, new ScreenPosition(x, y), new Size(), ObjectType.ENEMY);
			SpaceObjectController enemyController = new SpaceObjectController(enemy);
			new SpaceObjectView(this.observers.get(0).display, enemy).update();

			this.attachObject(enemyController);
		}
	}

	public void enemiesShoot() {
		List<SpaceObjectController> shooters = new ArrayList<SpaceObjectController>();
		for (SpaceObjectController object : objects) {
			if (object.hasShot()) {
				shooters.add(object);
			}
		}
		for (SpaceObjectController shooter : shooters) {
			shooter.shoot();
		}
	}

	public boolean testCollision(GameObject obj1, GameObject obj2) {
		// object 1 bounds
		int obj1_upperX = obj1.screenPosition.x;
		int obj1_upperY = obj1.screenPosition.y;
		int obj1_lowerX = obj1.screenPosition.x + obj1.size.width;
		int obj1_lowerY = obj1.screenPosition.y + obj1.size.height;

		// object 2 bounds
		int obj2_upperX = obj2.screenPosition.x;
		int obj2_upperY = obj2.screenPosition.y;
		int obj2_lowerX = obj2.screenPosition.x + obj2.size.width;
		int obj2_lowerY = obj2.screenPosition.y + obj2.size.height;

		return (((obj2_upperX >= obj1_upperX && obj2_upperX <= obj1_lowerX)
				&& (obj2_upperY >= obj1_upperY && obj2_upperY <= obj1_lowerY))
				|| ((obj1_upperX >= obj2_upperX && obj1_upperX <= obj2_lowerX)
						&& (obj1_upperY >= obj2_upperY && obj1_upperY <= obj2_lowerY))
				|| ((obj2_lowerX >= obj1_upperX && obj2_lowerX <= obj1_lowerX)
						&& (obj2_upperY >= obj1_upperY && obj2_upperY <= obj1_lowerY))
				|| ((obj1_lowerX >= obj2_upperX && obj1_lowerX <= obj2_lowerX)
						&& (obj1_upperY >= obj2_upperY && obj1_upperY <= obj2_lowerY))
				|| ((obj2_lowerX >= obj1_upperX && obj2_lowerX <= obj1_lowerX)
						&& (obj2_lowerY >= obj1_upperY && obj2_lowerY <= obj1_lowerY))
				|| ((obj1_lowerX >= obj2_upperX && obj1_lowerX <= obj2_lowerX)
						&& (obj1_lowerY >= obj2_upperY && obj1_lowerY <= obj2_lowerY))
				|| ((obj2_upperX >= obj1_upperX && obj2_upperX <= obj1_lowerX)
						&& (obj2_lowerY >= obj1_upperY && obj2_lowerY <= obj1_lowerY))
				|| ((obj1_upperX >= obj2_upperX && obj1_upperX <= obj2_lowerX)
						&& (obj1_lowerY >= obj2_upperY && obj1_lowerY <= obj2_lowerY)));
	}

	public void setScore() {
		Random rnd = new Random();
		this.ship.scorer.score += rnd.nextInt(91) + 10;
	}
}
