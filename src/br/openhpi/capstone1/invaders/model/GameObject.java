package br.openhpi.capstone1.invaders.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.openhpi.capstone1.invaders.controller.SpaceObjectController;
import br.openhpi.capstone1.invaders.view.Observer;

public abstract class GameObject implements Observable, Movable {

	// this is the base abstract class for every game object (element)
	// every element is observable by an "observer" (view) and can be moved along
	// the screen

	// every element has a position in the screen, a size, a direction to move and a
	// moving state (moving or stopped)...
	public ScreenPosition screenPosition;
	public Size size;
	public int direction;
	public ObjectType type;
	public boolean keepMoving = false;
	public boolean isExploding = false;
	public boolean exploded = false;
	public int explosionFrame = -1;
	protected boolean changeFrame = true;

	// shoot control
	public boolean hasShot;

	// ..., should keep tracking of the current score...
	public ScoreBoard scorer;

	// ...and be coupled to a background
	protected Background background;

	protected List<Observer> observers = new ArrayList<Observer>();

	public GameObject(ScoreBoard scorer, Background background, ScreenPosition screenPosition, Size size,
			int direction) {
		this.scorer = scorer;
		this.background = background;
		this.screenPosition = screenPosition;
		this.size = size;
		this.direction = direction;
		this.type = ObjectType.FIGHTER;
		this.hasShot = false;
	}

	public GameObject(ScoreBoard scorer, Background background, ScreenPosition screenPosition, Size size) {
		this(scorer, background, screenPosition, size, Direction.RIGHT);
		this.type = ObjectType.FIGHTER;
		this.hasShot = false;
	}

	@Override
	public void attach(Observer observer) {
		// attach an observer (view)
		observers.add(observer);
	}

	@Override
	public void notifyAllObservers() {
		// notify all attached observers to update their view state
		for (Observer observer : observers) {
			observer.update();
		}
	}

	@Override
	public void detachAllObservers() {
		// remove and clean all attached observers to update their view state
		while (observers.size() > 0) {
			observers.set(0, null);
			observers.remove(0);
		}
	}

	@Override
	public void move() {
		// game objects do not move when exploding
		if (this.isExploding) {
			this.explode();
			return;
		}
		
		// no action for exploded space objects
		if (this.exploded && this.type != ObjectType.FIGHTER) {
			return;
		}

		// stop game when there are no balls left
		if (this.scorer != null) {
			if (this.scorer.ships < 0) {
				this.keepMoving = false;
			} else {
				// exploded game objects must be re-spawn
				if (this.exploded) {
					this.spawn();
					return;
				}
			}
		}

		// moving is a standard behavior of every game element
		if (!this.keepMoving) {
			// when stopped it just notifies the observers and do nothing else
			notifyAllObservers();
			return;
		}

		// when moving it takes into account its current direction
		// and makes the appropriate adjustments to its position in the screen
		int offset = (this.type == ObjectType.FIGHTER_SHOT || this.type == ObjectType.ENEMY_SHOT) ? 10 : 5;
		switch (this.direction) {
		case Direction.LEFT:
			screenPosition.x -= offset;
			break;
		case Direction.RIGHT:
			screenPosition.x += offset;
			break;
		case Direction.DOWN:
			screenPosition.y += offset;
			break;
		case Direction.UP:
			screenPosition.y -= offset;
			break;
		case Direction.LEFT_DOWN:
			screenPosition.x -= offset;
			screenPosition.y += offset;
			break;
		case Direction.LEFT_UP:
			screenPosition.x -= offset;
			screenPosition.y -= offset;
			break;
		case Direction.RIGHT_DOWN:
			screenPosition.x += offset;
			screenPosition.y += offset;
			break;
		case Direction.RIGHT_UP:
			screenPosition.x += offset;
			screenPosition.y -= offset;
			break;
		}

		// it also may bounce when a boundary is hit
		this.bounce();

		// enemies also may shoot
		if (this.type == ObjectType.ENEMY) {
			if (this.hasShot) {
				this.hasShot = false;
			} else {
				Random rnd = new Random();
				if (rnd.nextInt(1001) > 500) {
					this.hasShot = true;
				}
			}
		}

		// all objects can collide with other objects
		this.collide();

		// finally the observers get notified about the changes made by the moving
		// behavior
		notifyAllObservers();
	}

	// most game objects of this game may shoot
	public void shoot() {
		// exploding or exploded objects do not shoot
		if(this.isExploding || this.exploded) {
			return;
		}
		// shots do not shoot
		if (this.type != ObjectType.FIGHTER_SHOT && this.type != ObjectType.ENEMY_SHOT) {
			this.background.addShot(this);
			this.hasShot = true;
		}
	}

	// most game objects of this game may explode
	public void explode() {
		if (changeFrame) {
			this.explosionFrame++;
			this.explosionFrame = (this.explosionFrame > 12) ? -1 : this.explosionFrame;
		}
		this.isExploding = (this.explosionFrame >= 0);
		this.exploded = !this.isExploding;
		this.keepMoving = !this.isExploding && !this.exploded;
		changeFrame = (this.exploded) ? true : !changeFrame;
		notifyAllObservers();
	}

	// objects can collide regardless of their type
	public void collide() {
		// when the object is exploding or exploded there's no collision
		if (this.isExploding || this.exploded) {
			return;
		}
		for (SpaceObjectController object : background.getObjects()) {
			// object cannot collide neither with itself nor with exploding (or exploded)
			// objects and their own shots
			if (object.getObject().equals(this) || object.getObject().isExploding || object.getObject().exploded
					|| ((this.type == ObjectType.FIGHTER && object.getObject().type == ObjectType.FIGHTER_SHOT))
					|| ((this.type == ObjectType.FIGHTER_SHOT && object.getObject().type == ObjectType.FIGHTER))
					|| ((this.type == ObjectType.ENEMY && object.getObject().type == ObjectType.ENEMY_SHOT))
					|| ((this.type == ObjectType.ENEMY_SHOT && object.getObject().type == ObjectType.ENEMY))) {
				continue;
			}
			if (background.testCollision(this, object.getObject())) {
				// when fighters and enemies collide with other objects they explode
				if (this.type == ObjectType.FIGHTER || this.type == ObjectType.ENEMY) {
					this.explode();
				}
				if (object.getObject().type == ObjectType.FIGHTER || object.getObject().type == ObjectType.ENEMY) {
					object.getObject().explode();
				}
				// when shots collide with other objects they're simply considered exploded
				if (this.type == ObjectType.FIGHTER_SHOT || this.type == ObjectType.ENEMY_SHOT) {
					this.exploded = true;
				}
				if (object.getObject().type == ObjectType.FIGHTER_SHOT
						|| object.getObject().type == ObjectType.ENEMY_SHOT) {
					object.getObject().exploded = true;
				}

				// when a fighter shot hits an enemy, the player must earn some points
				if ((this.type == ObjectType.FIGHTER_SHOT || this.type == ObjectType.ENEMY)
						&& (object.getObject().type == ObjectType.FIGHTER_SHOT
								|| object.getObject().type == ObjectType.ENEMY)) {
					background.setScore();
				}
			}
		}
	}

	// moving, shooting, colliding and exploding are standard behaviors for most
	// game elements, but each specific element may bounce and spawn in a different
	// way,
	// so bounce and spawn are abstract behavior that must be implemented by each
	// specific
	// game element
	protected abstract void bounce();

	protected abstract void spawn();
}
