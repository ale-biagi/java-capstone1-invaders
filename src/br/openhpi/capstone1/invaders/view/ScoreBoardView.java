package br.openhpi.capstone1.invaders.view;

import br.openhpi.capstone1.invaders.model.ScreenObject;
import br.openhpi.capstone1.invaders.model.ScoreBoard;
import br.openhpi.capstone1.invaders.model.Screen;
import processing.core.PApplet;
import processing.core.PImage;

public class ScoreBoardView extends Observer {

	// image to draw ships in the score board
	PImage shipImg;
	
	// this observer observes the state and behavior of the score board to update the screen
	// so it needs to have a score board object assigned...
	
	public ScoreBoardView(PApplet display, ScreenObject screen) {
		// ...which is done by its supper class constructor
		super(display, screen);
		shipImg = display.loadImage("spaceship-01.png", "png");
	}

	@Override
	public void update() {
		// the score board is drawn as a "rectangle" at the screen
		// panel
		display.fill(screen.color.red, screen.color.green, screen.color.blue);
		display.stroke(0, 0, 0);
		display.rect(this.screen.screenPosition.x, 
					this.screen.screenPosition.y, 
					this.screen.size.width, 
					this.screen.size.height);
		
		// score text
		display.fill(0);
		display.textSize(24);
		display.text("SCORE: " + ((ScoreBoard) this.screen).displayScore(), 12, 34);
		
		// remaining balls
		display.fill(255);
		int x = Screen.WIDTH - 120;
		for (int i = 0; i < ((ScoreBoard) this.screen).ships; i++) {
			display.image(shipImg, x, 12, shipImg.width/3, shipImg.height/3);
			x += 38;
		}

		// draw everything
 	    display.redraw();
	}
}
