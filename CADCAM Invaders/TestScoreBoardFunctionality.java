package assign3CST126CST133;

import java.io.File;

public class TestScoreBoardFunctionality {

	public static void main(String[] args) 
	{
		Player obPlayer1 = new Player("Bryce", 72);
		File obInput = new File("CSVFile/scoreboarddata.csv");
		
		Scoreboard testickle = new Scoreboard(obPlayer1, obInput, null);
		testickle.getScoreBoard().stream().forEach(x -> System.out.printf("%s %d\n", x.getName(), x.getScores()));
	}

}
