package assign3CST126CST133;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/*import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;*/

public class Scoreboard 
{
	private ArrayList<Player> obReference;
	private Player obData;
	private File obTextFile;
	public ImageView scoreBoard, buttonClose;
	private static final int NAME_POS = 0, SCORE_POS = 1;
	private int element;
	private Pane obParent;
	
	public Scoreboard (Player obTarget, File obInput, Pane obPane)
	{
		this.obData = obTarget;
		this.obTextFile = obInput;
		
		obReference = new ArrayList<Player>();
		obReference.addAll(loadInfo(obTextFile));
		
		this.obParent = obPane;
		
		addPlayer(obData);
		updateIt(obReference, obTextFile);
		scoreBoard = new ImageView(Sprite.ImageFolder+ "GameOverScreen/scoreboard.png");
		buttonClose = new ImageView(Sprite.ImageFolder+ "GameOverScreen/close_button.png");
		this.element = 0;
		
	}
	
	private static ArrayList<Player> loadInfo (File obInput)
	{
		ArrayList<Player> obReturn = new ArrayList<>();
		
		if (obInput.isFile() && !obInput.isDirectory())
		{
			try (Scanner userInput = new Scanner(obInput))
			{
				while (userInput.hasNext())
				{
					String sName = userInput.nextLine();
					String[] saSplit = sName.split(",");
					obReturn.add(new Player(saSplit[NAME_POS], Integer.parseInt(saSplit[SCORE_POS])));
				}
			} 
			
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return obReturn;
	}
	
	public void addPlayer(Player obPlayer)
	{
		obReference.add(obPlayer);
	}
	
	public void updateIt(ArrayList<Player> obPlayers, File obInput)
	{
			try (PrintWriter obWriteBack = new PrintWriter(obInput))
			{
				obPlayers.stream().sorted((x,y) -> y.getScores() - x.getScores()).forEach(x -> {obWriteBack.printf("%s,%d\n", x.getName(), x.getScores());});
			} 
			
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	public ArrayList<Player> getScoreBoard()
	{
		return this.obReference;
	}
	
	@SuppressWarnings("static-access")
	public Pane showAnimation()
	{
		Pane obReturn = new Pane();
		VBox obName = new VBox(30);
		VBox obScore = new VBox(30);
		HBox obCurrent = new HBox(55);
		
		
		obReturn.getChildren().add(scoreBoard);
		scoreBoard.setX(-1500);
		
		Timeline obMove5 = new Timeline(new KeyFrame(Duration.millis(1), f -> AnimationUtils.moveOne(scoreBoard, -2)));
		obMove5.setCycleCount(500);
		obMove5.play();

		obMove5.setOnFinished(e -> {
			
			obReference =  obReference.stream().sorted((x,y) -> y.getScores() - x.getScores()).collect(Collectors.toCollection(ArrayList::new));
			int positionLocation = obReference.indexOf(obData) + 1;
			
			obReturn.getChildren().addAll(obName, obScore, obCurrent);
			obName.setLayoutX(554);
			obName.setLayoutY(346);
			obScore.setLayoutX(897);
			obScore.setLayoutY(346);
			obCurrent.setLayoutX(713);
			obCurrent.setLayoutY(109);
			
			obCurrent.setMinWidth(546);
			
			Text tRank = new Text(Integer.toString(positionLocation));
			Text tName = new Text(obData.getName());
			Text tScore = new Text(Integer.toString(obData.getScores()));
			HBox ScoreBox = new HBox();
			ScoreBox.getChildren().add(tScore);
			ScoreBox.setAlignment(Pos.CENTER_RIGHT);
			//ScoreBox.setBorder(new Border(new BorderStroke(Color.BISQUE,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderWidths.DEFAULT)));
			
			tRank.setFill(Color.BLACK);
			tRank.setFont(Font.loadFont(Sprite.ImageFolder+ "Font/pixel.ttf", 47.06));
			tName.setFill(Color.BLACK);
			tName.setFont(Font.loadFont(Sprite.ImageFolder+ "Font/pixel.ttf", 47.06));
			tScore.setFill(Color.BLACK);
			tScore.setFont(Font.loadFont(Sprite.ImageFolder+ "Font/pixel.ttf", 47.06));
			tScore.setTextAlignment(TextAlignment.RIGHT);
			
			new Thread( () -> {
				
				Platform.runLater(new Thread( () -> {
					obCurrent.getChildren().addAll(tRank,tName,ScoreBox);
					obCurrent.setHgrow(tRank, Priority.NEVER);
					obCurrent.setHgrow(tName, Priority.SOMETIMES);
					obCurrent.setHgrow(ScoreBox, Priority.ALWAYS);
				}));
				
				for (element = 0; element < (obReference.size() < 3 ? obReference.size() : 3) ; element++)
				{

					Platform.runLater(new Thread( () -> {
						obName.getChildren().add(obReference.get(element).createNameText());
						obScore.getChildren().add(obReference.get(element).createNameScore());
						System.out.println(element);
					}));
					
					try 
					{
						Thread.sleep(1200);
					} 
					catch (InterruptedException f) 
					{
						// TODO Auto-generated catch block
						f.printStackTrace();
					}
					
					if (element == 2)
					{
						
						Platform.runLater(new Thread( () -> {
							obParent.getChildren().add(buttonClose);
							buttonClose.setX(1241);
							buttonClose.setY(641);
						}));
					}
				}
				buttonClose.setOnMouseClicked(f -> {
					//Platform.exit();
					System.exit(0);
				});
				
			}).start();			
		});
		return obReturn;
	}
}
