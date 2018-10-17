package br.openhpi.capstone1.invaders.model;

public class SpaceShip extends GameObject {

	private int initialX;
	private int initialY;

	public SpaceShip(ScoreBoard scorer, Background background) {
		// paddle is constructed with the default width and height of the class size and
		// positioned at screen center
		super(scorer, background, new ScreenPosition((int) ((Screen.WIDTH - new Size().width) / 2),
						Screen.HEIGHT - (new Size().height) - 5), new Size());
		// initial moving state is "stopped"
		this.keepMoving = false;
		// assign the scorer object
		this.scorer = scorer;
		// freeze the initial position for ship re-spawn
		initialX = this.screenPosition.x;
		initialY = this.screenPosition.y;
	}

	@Override
	public void spawn() {
		// reset state
		this.screenPosition.x = initialX;
		this.screenPosition.y = initialY;
		this.keepMoving = false;
		this.isExploding = false;
		this.exploded = false;

		// one ship less
		this.scorer.ships--;
		notifyAllObservers();
	}

	@Override
	protected void bounce() {
		if (this.screenPosition.x <= 0 || this.screenPosition.y <= this.scorer.size.height
				|| this.screenPosition.x + this.size.width >= Screen.WIDTH
				|| this.screenPosition.y + this.size.height >= Screen.HEIGHT) {
			// some bound has been reached
			// so the ship must move to the opposite side
			switch (this.direction) {
			case Direction.UP:
				this.direction = Direction.DOWN;
				break;
			case Direction.DOWN:
				this.direction = Direction.UP;
				break;
			case Direction.RIGHT:
				this.direction = Direction.LEFT;
				break;
			case Direction.LEFT:
				this.direction = Direction.RIGHT;
				break;
			case Direction.RIGHT_UP:
				this.direction = Direction.LEFT_DOWN;
				break;
			case Direction.LEFT_UP:
				this.direction = Direction.RIGHT_DOWN;
				break;
			case Direction.RIGHT_DOWN:
				this.direction = Direction.LEFT_UP;
				break;
			case Direction.LEFT_DOWN:
				this.direction = Direction.RIGHT_UP;
				break;
			}
		}
	}

}
