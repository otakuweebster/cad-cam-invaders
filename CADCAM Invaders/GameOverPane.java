package assign3CST126CST133;

import java.io.File;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GameOverPane extends Pane
{
	private Pane obGameOver, obParent;
	private ImageView bsod, glitchEffect, gameOver1, gameOver1BK, gameOver2;
	private Player obPlayer;
	private int score;
	public static final String CSVFilePath = "src/assign3CST126CST133/CADCAMInvadersImages/";
	
	public GameOverPane(Pane obTarget, Player obPerson, int Score) 
	{
		this.obParent = obTarget;
		obGameOver = new Pane();
		obParent.getChildren().add(obGameOver);
		score = Score;
		this.obPlayer = obPerson;
		
		bsod = new ImageView(Sprite.ImageFolder+ "GameOverScreen/bsod2.png");
		glitchEffect = new ImageView(Sprite.ImageFolder+ "GameOverScreen/glitch1.png");
		gameOver1 = new ImageView(Sprite.ImageFolder+ "GameOverScreen/gms3.png");
		gameOver1BK = new ImageView(Sprite.ImageFolder+ "GameOverScreen/gms3_bk.png");
		gameOver2 = new ImageView(Sprite.ImageFolder+ "GameOverScreen/gms4.png");
		
		gameOverAnimation();
		
	}
	
	public void gameOverAnimation()
	{
		obGameOver.getChildren().addAll(gameOver1BK, bsod, glitchEffect, gameOver1, gameOver2);
		gameOver1.setX(1300);
		gameOver2.setY(-500);
		
		PauseTransition obPause = new PauseTransition(Duration.millis(500));
		PauseTransition obPause2 = new PauseTransition(Duration.millis(2500));
		PauseTransition obPause3 = new PauseTransition(Duration.millis(3000));
		
		Timeline obDelete = new Timeline(new KeyFrame(Duration.millis(50), e -> obGameOver.getChildren().remove(glitchEffect)));
		Timeline obMove = new Timeline(new KeyFrame(Duration.millis(2), e -> AnimationUtils.moveTwo(gameOver1, 2)));
		obMove.setCycleCount(500);
		
		FadeTransition bsodFadeOut = new FadeTransition(Duration.millis(500), bsod);
		bsodFadeOut.setFromValue(1.0);
		bsodFadeOut.setToValue(0.0);
		
		Timeline obMove2 = new Timeline(new KeyFrame(Duration.millis(2), e -> AnimationUtils.moveDown(gameOver2, 2)));
		obMove2.setCycleCount(400);

		SequentialTransition obSequence = new SequentialTransition(obPause, obDelete, obPause2, bsodFadeOut, obMove, obMove2, obPause3);
		obSequence.play();
		obSequence.setOnFinished(e ->{
			
			obPlayer.setScore(score);
			
			Timeline obMove3 = new Timeline(new KeyFrame(Duration.millis(2), f -> AnimationUtils.moveUp(gameOver2, -500)));
			obMove3.setCycleCount(400);
			obMove3.play();
			
			Timeline obMove4 = new Timeline(new KeyFrame(Duration.millis(1), f -> AnimationUtils.moveOne(gameOver1, 1550)));
			obMove4.setCycleCount(600);
			obMove4.play();
			
			Scoreboard obStats = new Scoreboard(obPlayer, new File(CSVFilePath + "ScoreBoardData.csv"), obParent);
			obGameOver.getChildren().add(obStats.showAnimation());
			
		});
	}
	
	

}
