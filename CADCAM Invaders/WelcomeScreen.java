package assign3CST126CST133;


import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomeScreen 
{
	public Pane obWelcome;
	public StackPane stuff;
	private Pane obParent;
	private ImageView ccCom, ccStudent, cstJava, cstStudent, gameAck, logo, studiosLogo, cStudent, caStudent, obButton, 
					  obPrompt1, obPrompt2;
	private Timeline movement;
	public String playerName;
	private TextField userName;
	private SequentialTransition sqTran;
	public static Player obPlayer;
	public Stage obStage;
		
	public WelcomeScreen(Pane obTarget, Stage MainStage)
	{
		
		this.obParent = obTarget;
		obStage = MainStage;
		obWelcome = new Pane();
		movement = null;
		userName = new TextField();
		stuff = new StackPane();
		
		stuff.getChildren().add(obWelcome);
		obParent.getChildren().add(stuff);
		
		ccCom = new ImageView(Sprite.ImageFolder+ "Intro/CADCAM_COMPASS.png");
		ccStudent = new ImageView(Sprite.ImageFolder+ "Intro/CADCAM_STUDENT.png");
		cstJava = new ImageView(Sprite.ImageFolder+ "Intro/CST_JAVA.png");
		cstStudent = new ImageView(Sprite.ImageFolder+ "Intro/CST_STUDENT.png");
		gameAck = new ImageView(Sprite.ImageFolder+ "Intro/javafx.png");
		logo = new ImageView(Sprite.ImageFolder+ "WelcomeScreen/cadcam_invaders_logo.png");
		studiosLogo = new ImageView(Sprite.ImageFolder+ "Intro/studios.png");
		
		cStudent = new ImageView(Sprite.ImageFolder+ "WelcomeScreen/cst1.png");
		caStudent = new ImageView(Sprite.ImageFolder+ "WelcomeScreen/alien1.png");
		obButton = new ImageView(Sprite.ImageFolder+ "WelcomeScreen/button_start.png");
		
		obPrompt1 = new ImageView(Sprite.ImageFolder+ "WelcomeScreen/EnterNamePrompt.png");
		obPrompt2 = new ImageView(Sprite.ImageFolder+ "WelcomeScreen/TooShortPrompt.png");
		
		introScreen();
	}


	private void introScreen() 
	{
		obParent.setStyle("-fx-background-color: black");
		obWelcome.getChildren().addAll(studiosLogo,gameAck);
		
		gameAck.setX(253);
		gameAck.setY(365);
		
		FadeTransition ft1 = new FadeTransition(Duration.millis(1000), studiosLogo);
		ft1.setFromValue(0.0);
		ft1.setToValue(1.0);
		
		PauseTransition pt1 = new PauseTransition(Duration.millis(1500));
		
		FadeTransition ft2 = new FadeTransition(Duration.millis(1000), studiosLogo);
		ft2.setFromValue(1.0);
		ft2.setToValue(0);
		
		FadeTransition ft3 = new FadeTransition(Duration.millis(1000), gameAck);
		ft3.setFromValue(0.0);
		ft3.setToValue(1.0);
		
		PauseTransition pt2 = new PauseTransition(Duration.millis(1500));
		
		FadeTransition ft4 = new FadeTransition(Duration.millis(1000), gameAck);
		ft4.setFromValue(1.0);
		ft4.setToValue(0);
		
		PauseTransition pt3 = new PauseTransition(Duration.millis(500));

		Timeline executeIntro = new Timeline(new KeyFrame(Duration.millis(1), e -> {
			introSequenceOne();
		}));
		
		sqTran = new SequentialTransition(ft1, pt1, ft2, ft3, pt2, ft4, pt3, executeIntro);
		sqTran.play();
		
	}
	
	public void introSequenceOne()
	{
		sqTran.stop();
		
		BackgroundImage bImg = new BackgroundImage(new Image(Sprite.ImageFolder+ "Intro/bck1.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		obParent.setBackground(new Background(bImg));
		
		obWelcome.getChildren().addAll(ccCom, ccStudent);
		
		//Animation Time
		ccCom.setX(-500);
		ccStudent.setX(-300);
		movement = new Timeline(new KeyFrame(Duration.millis(3), e -> AnimationUtils.moveOne(ccStudent, 0)));
		movement.setCycleCount(500);
		movement.play();
		
		movement = new Timeline(new KeyFrame(Duration.millis(2), e -> AnimationUtils.moveOne(ccCom, 1400)));
		movement.setCycleCount(1000);
		movement.play();
		
		movement = new Timeline(new KeyFrame(Duration.millis(1900), e -> introSequenceTwo()));
		movement.play();
	}
	
	public void introSequenceTwo()
	{
		obWelcome.getChildren().removeAll(ccCom, ccStudent);
		movement.stop();

		
		BackgroundImage bImg = new BackgroundImage(new Image(Sprite.ImageFolder+ "Intro/bck2.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		obParent.setBackground(new Background(bImg));
		
		obWelcome.getChildren().addAll(cstJava, cstStudent);
		
		//Animation Time
		cstJava.setX(1368);
		cstStudent.setX(1366);
		
		movement = new Timeline(new KeyFrame(Duration.millis(1.5), e -> AnimationUtils.moveTwo(cstStudent, 0)));
		movement.setCycleCount(500);
		movement.play();
		
		movement = new Timeline(new KeyFrame(Duration.millis(2), e -> AnimationUtils.moveTwo(cstJava, -1400)));
		movement.setCycleCount(1000);
		movement.play();
		
		movement = new Timeline(new KeyFrame(Duration.millis(2000), e -> welcomeScreen()));
		movement.play();
		
	}
	
	public void welcomeScreen()
	{
		movement.stop();
		obWelcome.getChildren().removeAll(cstJava, cstStudent);
		obWelcome.getChildren().addAll(cStudent, caStudent, logo);
		
		BackgroundImage bImg = new BackgroundImage(new Image(Sprite.ImageFolder+ "WelcomeScreen/wm_screen.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		obParent.setBackground(new Background(bImg));
		
		//Animation Time
		cStudent.setX(1369);
		cStudent.setY(14);
		caStudent.setX(-400);
		
		logo.setY(-100);
		logo.setX(318);
		
		Timeline move2Right = new Timeline(new KeyFrame(Duration.millis(5), e -> AnimationUtils.moveOne(caStudent, -5)));
		move2Right.setCycleCount(400);
		move2Right.play();
		
		Timeline move2Left = new Timeline(new KeyFrame(Duration.millis(5), e -> AnimationUtils.moveTwo(cStudent, 1025)));
		move2Left.setCycleCount(400);
		move2Left.play();
		
		Timeline move2Down = new Timeline(new KeyFrame(Duration.millis(5), e -> AnimationUtils.moveDown(logo, 116)));
		move2Down.setCycleCount(400);
		move2Down.play();
		
		
		Timeline showTextField = new Timeline(new KeyFrame(Duration.millis(2000), e -> {
			obWelcome.getChildren().add(userName);
			userName.setLayoutX(540);
			userName.setLayoutY(549);
			userName.setFont(Font.loadFont(Sprite.ImageFolder+ "Font/pixel.ttf", 24));
			obWelcome.getChildren().add(obPrompt1);
		}));
		showTextField.play();
		
		userName.setOnAction(e -> {
			
			if (userName.getText().trim().length() >= 4)
			{
				if (obWelcome.getChildren().contains(obPrompt1))
				{
					obWelcome.getChildren().remove(obPrompt1);
				}
				
				if (obWelcome.getChildren().contains(obPrompt2))
				{
					obWelcome.getChildren().remove(obPrompt2);
				}
				
				if (!obWelcome.getChildren().contains(obButton))
				{
					obWelcome.getChildren().add(obButton);
					obButton.setX(587);
					obButton.setY(638);
				}
				
				playerName = userName.getText();
			}
			 
			else
			{
				//Just a preventive measure when the user decides to trick the system
				if (userName.getText().trim().length() < 4)
				{
					if (obWelcome.getChildren().contains(obPrompt1))
					{
						obWelcome.getChildren().remove(obPrompt1);
					}
					
					if (obWelcome.getChildren().contains(obButton))
					{
						obWelcome.getChildren().remove(obButton);
					}
					
					if (!obWelcome.getChildren().contains(obPrompt2))
					{
						obWelcome.getChildren().remove(obPrompt1);
						obWelcome.getChildren().add(obPrompt2);
					}
				}
			}
		});
		
		obButton.setOnMouseClicked(e -> {

			obPlayer = new Player(playerName.toUpperCase());
			System.out.println(getPlayer().getName());
			
			
			Invasion TheGame = new Invasion(obPlayer, obParent, 1);
			
			try {
				
				TheGame.start(obStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});
		
	}
	
	public static Player getPlayer()
	{
		return obPlayer;
	}
	
	
}
