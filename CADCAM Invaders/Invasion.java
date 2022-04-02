package assign3CST126CST133;


import java.io.File;
import java.util.ArrayList;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Invasion extends Application
{
	private Stage TheMainStage;
	private Player obPlayer;
	private ArrayList<Image> obUFOs;
	private ArrayList<Sprite> lstShells;
	private ArrayList<Sprite> SpentShells;
	private ArrayList<Sprite> Aliens;
	private ArrayList<Sprite> Graveyard;
	private Canvas obCanvas;
	private GraphicsContext gc;
	private int Difficulty; // Difficulty  = Level Number * 3
	private int Shots = 0;
	private int Kills = 0;
	private int Score;
	private Sprite Launcher;
	private Text numKills = new Text("0");
	private Text numShots = new Text("0");
	private Text ScoreBox;
	private boolean Dancing = false;
	private boolean Loading = false; // a boolean value to be used to limit the rate at which the player can fire.
	private Pane obPane;
	private ImageView transitionRoll, yWinBk, yWinSprite1, yWinSprite2, continueBtn, imDoneBtn, cutOut;
	private Stage obRef;
	public boolean GameDone = false;
	private static final String CSVFilePath = "src/assign3CST126CST133/CADCAMInvadersImages/";
	
	public Invasion(Player NewGuy, Pane obMain, int Level)
	{
		Difficulty = Level * 3;
		obPlayer = NewGuy;
		obPane = obMain;
	}

	@Override
	public void start(Stage obStage) throws Exception
	{
		//Maybe an Animation here?
		this.obRef = obStage;
		transitionPane();
		
	}
	
	public void initializeGame(Stage obStage)
	{
		TheMainStage = obStage;
		lstShells = new ArrayList<>();
		SpentShells = new ArrayList<>();
		Graveyard = new ArrayList<>();
		Aliens = new ArrayList<>();
		
		if(Difficulty>=30 || Difficulty % 9 == 0)
		{
			Dancing = true;
		}
		
		for (int j = 0; j < Difficulty/10+1; j++)  
		{
			for (int i = 0; i<(Difficulty/5)+4-Difficulty/10; i++)
			{
				Sprite UFO = new Sprite(obUFOs.get(0), 80.0 + (100 * i), 0.0-(j*100)); //Change the 75 for images of different widths.
				if (Dancing && Math.random() * 20 > 19)
				{
					UFO.dodge(gc);
				}
				Aliens.add(UFO);
			}
		
		}
		
		Launcher = new Sprite(new Image(Sprite.ImageFolder + "Sprites/Launch.png"), 352,640);
		Launcher.render(gc);
		
		startTask();		
		
		obStage.setTitle("Level " + Difficulty/3);
		obStage.show();
		
		obPane.requestFocus();

		obPane.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.LEFT && Launcher.isLive() && Launcher.getCoors().getX() >= 53)
			{
				Launcher.clear(gc);
				Launcher.getCoors().decX(Difficulty/2);
				Launcher.render(gc);
				
				
			}
			if (e.getCode() == KeyCode.RIGHT  && Launcher.isLive() && Launcher.getCoors().getX() <= 658)
			{
				
				Launcher.clear(gc);
				Launcher.getCoors().incX(Difficulty/2);
				Launcher.render(gc);
				
			}
			
			if (e.getCode() == KeyCode.UP  && Launcher.isLive() && !Loading)
			{
				Thread Reload = new Thread(()->{
					try {
						Thread.sleep(3000/Difficulty);
						Platform.runLater(()->{
							Loading = false;
						});
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				});
				
				Loading = true;
				Image imShell = new Image(Sprite.ImageFolder + "Sprites/shell.png");
				
				Sprite obShell = new Sprite(imShell, Launcher.getCoors().getX() + Launcher.getImage().getWidth()/2 - imShell.getWidth()/2,
						Launcher.getCoors().getY()-10);
				obShell.render(gc);
				
				lstShells.add(obShell);
				
				
				Reload.start();
			}
			
		});
		
	}
	
	/**
	 * This method will attempt to load all of the images from the specified directory that are pngs into the obUFOs array lists
	 * The idea is that all of the pngs make up an animation for us.
	 * @param sPath
	 */
	private void loadImages()
	{
		obUFOs= new ArrayList<>();
		
		for (int i =0; i<6; i++ )
		{
			obUFOs.add( new Image(Sprite.ImageFolder + "Sprites/ufo_" + i + ".png"));
			
		}
	}
	

	/**
	 * This method launches the game and starts the motion for all of the elements in the game. 
	 */
	private void startTask()
	{	
		
		for (Sprite UFO: Aliens)
		{
			Thread obThread = new Thread ( () ->runTask(UFO));
			obThread.setDaemon(true);
			obThread.start();
		}
			
		Thread obGThread = new Thread( () -> runBarrage());
		obGThread.setDaemon(true);
		obGThread.start();
		
	}
	

	/**
	 * This method is the main Alien method, and checks each Alien to see if he is still in play or if he's reached the bottom
	 * if he has done neither, he will fall closer. If he HAS reached the bottom, the game ends.
	 * @param UFO
	 */
	private void runTask(Sprite UFO)
	{
		try
		{
			int nPos = 0;
			
			while (UFO.isLive())
			{
				if (UFO.intersects(Launcher) && Launcher.isLive())
				{
					UFO.Hit();
					Launcher.Hit();
					Platform.runLater(()->
					{
						
						clearUFO(UFO);
						clearLauncher();
						
					});
					continue;
				}		
				
				if( UFO.getCoors().getY()>700)
				{
					Platform.runLater( () -> GameOver() );
					return;
				}
				if (UFO.getCoors().getX()<1000)
				{
					
					UFO.setImage(obUFOs.get(nPos % obUFOs.size()));
					nPos++;
					Platform.runLater(() -> {
					if (Dancing && Math.random() * 20 > 19)
					{
						UFO.dodge(gc);
					}
					gravity(UFO);
					});
					Thread.sleep(75);
				}
			}
		
			
		}
		catch (InterruptedException exp)
		{
			exp.printStackTrace();
		}
		
	}
	
	/**
	 * This Method will make the Alien Invader fall farther down to earth
	 * @param obSprite
	 */
	private void gravity(Sprite obSprite)
	{
		obSprite.clear(gc);
		obSprite.getCoors().incY( (Difficulty>6) ? (Difficulty/6) : 1);
		obSprite.render(this.gc);
	}
	
	
	
	/**
	 * This is the primary method for the motion of all fired Shells across the screen.	
	 */
	private void runBarrage()
	{
		while(!Aliens.isEmpty())
		{
			for( Sprite obSprite : lstShells)
			{
				
				if (obSprite.getCoors().getY() < 0)
				{
					obSprite.Hit();
				}
				
				if (obSprite.isLive()) 
				{
					Platform.runLater(() -> track(obSprite));
				}
				
				else
				{
					Platform.runLater(() ->clearShell(obSprite));
				}
				
			}
			
			try
			{
				Thread.sleep(50);
			}
			catch (InterruptedException exp)
			{
				exp.printStackTrace();
			}

		}
				
	}
	
	/**
	 * This method handles the movement of a single Shell, and tracks to see if it has intercepted any UFOs
	 * @param Shell
	 */
	private void track(Sprite Shell)
	{
		Shell.clear(gc);
		Shell.getCoors().decY(Difficulty);
		Shell.render(gc);
		
		//Check to see if we have an interception
		if (!Aliens.isEmpty())
		{
			for (Sprite UFO:Aliens)
			{
				if (UFO.isLive() && Shell.intersects(UFO))
				{
					Shell.Hit();
					UFO.Hit();
					UFO.setImage(new Image(Sprite.ImageFolder + "Sprites/bang1.png"));
					UFO.render(gc);
					Thread obThread = new Thread ( () ->coolExplosion(UFO));
					obThread.setDaemon(true);
					obThread.start();		
				}
					
			}
		}
		
	}
	
	
	/**
	 * This method takes in a sprite (Preferably a UFO) that has been hit, and removes it from the field of play in an awesome explosion.
	 * @param UFO
	 */
	private void coolExplosion(Sprite UFO)
	{
		for (int i=1; i<=9; i++)
		{
				UFO.setImage(new Image(Sprite.ImageFolder + "Sprites/bang" + i +".png"));
				Platform.runLater( () -> gravity(UFO));
				try
				{
					Thread.sleep(100);
				}
				catch (InterruptedException exp)
				{
					
				}
		}
		clearUFO(UFO);
	}
	
		public void clearLauncher()
	{
		Launcher.clear(gc);
		Launcher.setImage(new Image(Sprite.ImageFolder + "Sprites/bang1.png"));
		Launcher.render(gc);
		
	}
	
		
	/**
	 * This method will take in a UFO sprite and clear it from the screen and place it into the Graveyard
	 * @param UFO
	 */
	public void clearUFO(Sprite UFO)
	{
		Aliens.remove(UFO);
		Graveyard.add(UFO);
		
		UFO.clear(gc);
		UFO.setImage(obUFOs.get(0));
		UFO.getCoors().setX(1001 + (UFO.getImage().getWidth()+5)*(Kills%6));
		UFO.getCoors().setY(150 + 40*(Kills/6));
		UFO.render(gc);
		
		Kills++;
		numKills.setText(Integer.toString(Kills));
		Score += Difficulty*2;
		ScoreBox.setText(Integer.toString(Score));
		
		if (Aliens.isEmpty())
		{
			Platform.runLater(()->YouWin());
		}
				
	}
	
	/**
	 * This method will take in a Shell sprite and clear it from the screen, placing it into the spent shells stack
	 * @param Shell
	 */
	public void clearShell(Sprite Shell)
	{
		Shell.clear(gc);
		lstShells.remove(Shell);
		Shell.getCoors().setX(1001 + (Shell.getImage().getWidth()+1)*(Shots%15));
		Shell.getCoors().setY(500 + 15*(Shots/15));
		Shell.render(gc);
		SpentShells.add(Shell);
		Shots++;
		numKills.setText(Integer.toString(Kills));
		Score -= 1;
		ScoreBox.setText(Integer.toString(Score));

	}
		
	private void GameOver()
	{
		lstShells.forEach(x->x.Hit());
		lstShells.clear();
		Aliens.forEach(x->x.Hit());
		Aliens.clear();
		
		obPlayer.setScore(obPlayer.getScores() + Score);
		
		obPane.getChildren().clear();
		obPane.getChildren().add(new GameOverPane(obPane, obPlayer, obPlayer.getScores()));
			
	}
	
	private void YouWin()
	{
		yWinBk = new ImageView(Sprite.ImageFolder + "Invaders/goodJob_bk.png");
		yWinSprite1 = new ImageView(Sprite.ImageFolder + "Invaders/goodJob1.png");
		yWinSprite2 = new ImageView(Sprite.ImageFolder + "Invaders/mission_accomplished.png");
		continueBtn = new ImageView(Sprite.ImageFolder + "Invaders/continueBtn.png");
		imDoneBtn = new ImageView(Sprite.ImageFolder + "Invaders/imDoneBtn.png");
		
		obPane.getChildren().addAll(yWinBk, yWinSprite1, yWinSprite2);
		yWinSprite1.setX(1369);
		yWinSprite2.setY(-1000);
		
		FadeTransition obFadeIn = new FadeTransition(Duration.millis(500), yWinBk);
		obFadeIn.setFromValue(0.0);
		obFadeIn.setToValue(1.0);
		obFadeIn.play();
		
		Timeline obSprite1 = new Timeline(new KeyFrame(Duration.millis(1), e-> AnimationUtils.moveTwo(yWinSprite1, 0)));
		obSprite1.setCycleCount(500);
		obSprite1.play();
		
		Timeline obSprite2 = new Timeline(new KeyFrame(Duration.millis(1.5), e-> AnimationUtils.moveDown(yWinSprite2, 0)));
		obSprite2.setCycleCount(500);
		obSprite2.play();
		
		obSprite2.setOnFinished(e -> {
			obPlayer.setScore(obPlayer.getScores()+Score);
            Score = 0;
            Kills = 0;
            Shots = 0;
			PauseTransition obPause = new PauseTransition(Duration.millis(500));
			obPause.play();
			obPause.setOnFinished(f -> {
				obPane.getChildren().addAll(continueBtn, imDoneBtn);
				
				continueBtn.setX(916);
				continueBtn.setY(642);
				imDoneBtn.setX(1127);
				imDoneBtn.setY(642);
			});
		});
		
		continueBtn.setOnMouseClicked(e->{
            try {
                
                Difficulty += 3;
                
        		numKills.setText(Integer.toString(Kills));
				ScoreBox.setText(Integer.toString(Score));
				numShots.setText(Integer.toString(Shots));
				Dancing = false;
				
                this.start(TheMainStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
		
		imDoneBtn.setOnMouseClicked(e -> {
			Timeline obSprite3 = new Timeline(new KeyFrame(Duration.millis(1.5), f-> AnimationUtils.moveOne(yWinSprite1, 1369)));
			obSprite3.setCycleCount(500);
			obSprite3.play();
			
			Timeline obSprite4 = new Timeline(new KeyFrame(Duration.millis(1.5), f-> AnimationUtils.moveDown(yWinSprite2, 800)));
			obSprite4.setCycleCount(500);
			obSprite4.play();
			
			Scoreboard obStats = new Scoreboard(obPlayer, new File(CSVFilePath + "ScoreBoardData.csv"), obPane);
			obPane.getChildren().removeAll(continueBtn, imDoneBtn);
			obPane.getChildren().add(obStats.showAnimation());
		});
        		
	}
	
	public void StartGame()
	{
		Application.launch();
	}
	
	public void transitionPane()
	{
		Pane transPane = new Pane();
		transitionRoll = new ImageView(Sprite.ImageFolder + "Invaders/shutter_transition.png");
		cutOut = new ImageView(Sprite.ImageFolder + "Invaders/cutout_bg.png");
		transPane.getChildren().add(transitionRoll);
		transitionRoll.setY(-1500);
		
		obPane.getChildren().add(transPane);
		
		Timeline obDown = new Timeline(new KeyFrame(Duration.millis(2), e -> {
			AnimationUtils.moveDown(transitionRoll, 0);
		}));
		
		obDown.setCycleCount(500);
		obDown.play();
		obDown.setOnFinished(e ->
		{
			obPane.getChildren().clear();
			obPane.setBackground(null);
			
			loadImages();
			this.obCanvas = new Canvas(1366,768);
			this.gc = this.obCanvas.getGraphicsContext2D();
			
			Score = 0;
			ScoreBox = new Text(Integer.toString(Score));
			ScoreBox.setFont(Font.loadFont(Sprite.ImageFolder + "Font/pixel.ttf", 42.65));
			ScoreBox.setFill(Color.WHITE);
			ScoreBox.setX(793);
			ScoreBox.setY(235);
			
			numKills.setFont(Font.loadFont(Sprite.ImageFolder + "Font/pixel.ttf", 42.65));
			numShots.setFont(Font.loadFont(Sprite.ImageFolder + "Font/pixel.ttf", 42.65));
			
			numKills.setX(793);
			numKills.setY(459);
			numKills.setFill(Color.WHITE);
			
			numShots.setX(793);
			numShots.setY(680);
			numShots.setFill(Color.WHITE);
			
			
			obPane.getChildren().addAll(obCanvas,cutOut,ScoreBox,numShots,numKills,transPane);
			BackgroundImage bImg = new BackgroundImage(new Image(Sprite.ImageFolder + "Invaders/game_bg.png"),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
			obPane.setBackground(new Background(bImg));
			
			Timeline obInitialize = new Timeline(new KeyFrame(Duration.millis(1200), f -> initializeGame(obRef)));
			obInitialize.play();
			
			PauseTransition obPause = new PauseTransition(Duration.millis(1000));
			obPause.play();
			obPause.setOnFinished(g ->{
				Timeline obUP = new Timeline(new KeyFrame(Duration.millis(2), h -> {
					AnimationUtils.moveUp(transitionRoll, -1500);}));
				obUP.setCycleCount(500);
				obUP.play();
				
				obUP.setOnFinished(i -> obPane.getChildren().remove(transPane));
			});
		});
	}
}
