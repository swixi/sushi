import java.util.ArrayList;
import java.util.List;

public class Sushi {
	public static boolean descriptiveNames = false;
	
	public static void main(String[] args) {
		new Sushi().run();
		System.exit(0);
	}
	
	public void run() {
		System.out.println("MMMM SUSHI GO!");
		
		int[] test = new int[]{-1, 1, 12, 1, 12};
		int[] test2 = new int[]{0, 1, -1, 1};
		int[] test3 = new int[]{0, 0, 1, 0, 0};
		int[] test4 = new int[]{0, 0, 0};
		int[] test5 = new int[]{0, 0, 2, 2, 1};
		int[] test6 = new int[]{1, 0, 0};
		
		System.out.println("" + Util.max(test) + Util.max(test2) + Util.max(test3) + Util.max(test4) + Util.max(test5) + Util.max(test6));
		
		while(true) {
			int userChoice = Util.intMenu("\nMain menu", new String[]{"New game", "Options", "Hand score test", "Exit"});
			
			if(userChoice == 1)
				newGame();
			else if(userChoice == 2) {
				int optionsChoice = Util.intMenu("\nOptions", new String[]{"Toggle descriptive names (" + 
						(descriptiveNames ? "on" : "off") + ")", "Back to main menu"});
				
				if(optionsChoice == 1)
					descriptiveNames = !descriptiveNames;
				else
					continue;
			}
			else if(userChoice == 3) {
				//userChoice = -1;
				
			}
			else if(userChoice == 4)
				break;
		}		
	}
	
	public void newGame() {
		int playerCount = Util.intMenu("\nSelect number of players (2-5):", 2, 5);
		new Game(playerCount);
	}	
}
