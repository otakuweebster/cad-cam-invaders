package assign3CST126CST133;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Player
{
	private String playerName;
	private int scores = 0;
	
	public Player(String name)
	{
		this.playerName = name;
	}
	
	public Player(String name, int nScore)
	{
		this.playerName = name;
		this.scores = nScore;
	}
	
	public void upScore()
	{
		scores++;
	}
	
	public String getName()
	{
		return this.playerName;
	}
	
	public int getScores()
	{
		return this.scores;
	}

	public Text createNameText()
	{
		Text obReturn = new Text(getName());
		obReturn.setFill(Color.WHITE);
		obReturn.setFont(Font.loadFont(Sprite.ImageFolder+ "Font/pixel.ttf", 57.73));
		
		return obReturn;
	}
	
	public Text createNameScore()
	{
		Text obReturn = new Text(Integer.toString(getScores()));
		obReturn.setFill(Color.WHITE);
		obReturn.setFont(Font.loadFont(Sprite.ImageFolder+ "Font/pixel.ttf", 57.73));
		
		return obReturn;
	}
	
	public void setScore(int nScore)
	{
		this.scores = nScore;
	}
	
	public int compareTo(Player obCompare) 
	{
		return this.scores - obCompare.scores;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Player ? ((Player) obj).getName().equals(this.getName()) && 
				((Player) obj).getScores() == this.getScores()  : false;
	}


	
	
}
