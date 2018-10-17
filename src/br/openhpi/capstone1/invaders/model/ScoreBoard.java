package br.openhpi.capstone1.invaders.model;

public class ScoreBoard extends ScreenObject {
	
	// the score board object controls the score and the number of balls left
	public int score = 0;
	public int ships = 2;

	public ScoreBoard() {
		super(new ScreenPosition(), new Size(Screen.WIDTH-1, 50), new RGBColor(255,255,0));
	}
	
	// the score must be displayed using a "999999" mask
	public String displayScore( ) {
		String formatedScore = this.score + "";
		
		if (this.score < 100000) {
			while(formatedScore.length() < 6) formatedScore = "0" + formatedScore;
		}
		
		return(formatedScore);
	}
}
