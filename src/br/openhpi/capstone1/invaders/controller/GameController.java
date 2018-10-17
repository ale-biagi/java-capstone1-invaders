package br.openhpi.capstone1.invaders.controller;

import br.openhpi.capstone1.invaders.model.*;
import br.openhpi.capstone1.invaders.view.*;
import processing.core.PApplet;
import processing.event.KeyEvent;

public class GameController {

	private PApplet display;

	// define the screen elements (screen objects) and their respective controllers
	private ScoreBoard scoreBoard;
	private ScreenObjectController scoreBoardController;

	private Background background;
	private ScreenObjectController backgroundController;

	// define the game elements (game objects) and their respective controllers
	private SpaceShip ship;
	private SpaceShipController shipController;

	public GameController(PApplet display) {
		this.display = display;

		scoreBoard = new ScoreBoard();
		scoreBoardController = new ScreenObjectController(scoreBoard);
		new ScoreBoardView(this.display, scoreBoard).update();

		background = new Background();
		ship = new SpaceShip(scoreBoard, background);
		background.setShip(ship);
		backgroundController = new ScreenObjectController(background);
		new BackgroundView(this.display, background);
		background.spawnEnemies(7);

		shipController = new SpaceShipController(ship, background);
		new SpaceShipView(this.display, ship).update();
	}

	public void handleGame() {
		backgroundController.draw();
		scoreBoardController.draw();
		shipController.move();
	}

	public void handleKeyEvent(KeyEvent event) {
		// restart game if it was over
		if (scoreBoard.ships < 0 && event.getKeyCode() == 17) {
			scoreBoard.score = 0;
			scoreBoard.ships = 2;
			background.keepMoving = true;
			background.detachAllObjects();
			background.spawnEnemies(7);
		} else {
			// handle the key pressing event
			shipController.handleKeyEvent(event);
		}
	}
}
