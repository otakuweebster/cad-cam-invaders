package assign3CST126CST133;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CADCAMInvadersGame extends Application 
{

	public static Pane obMain;
	public static WelcomeScreen obWelcome;
	public static GameOverPane imInPain;
	public static Player obPlayer;
	@Override
	public void start(Stage obStage) throws Exception 
	{
		obMain = new Pane();
		obWelcome = new WelcomeScreen(obMain,obStage);
		
		obStage.setScene(new Scene(obMain, 1366,768));
		obStage.getIcons().add(new Image("file:src/assign3CST126CST133/CADCAMInvadersImages/icon.png"));
		obStage.setTitle("The Invasion of the Evil Mutant CADCAM Aliens - COSC190 Assignment # 3");
		obStage.setResizable(false);
		obStage.show();
	}

	public static void main(String[] args) 
	{
		Application.launch(args);

	}
	

}
